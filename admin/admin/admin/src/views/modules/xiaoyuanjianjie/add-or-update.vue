<template>
	<div class="addEdit-block" style="width: 100%;">
		<div class="editor-wrap">
			<h3 class="editor-title">校园简介编辑</h3>

			<!-- 编辑区 -->
			<div class="edit-section">
				<div class="field-row">
					<label class="field-label">封面图片</label>
					<div class="field-control">
						<div v-if="coverImage" class="cover-preview-inline">
							<img :src="coverImage.substring(0,4)=='http' ? coverImage : $base.url + coverImage" />
							<button class="btn-remove" @click="coverImage = ''">移除</button>
						</div>
						<file-upload
							v-else
							tip="点击上传封面图片"
							action="file/upload"
							:limit="1"
							:multiple="false"
							:fileUrls="''"
							@change="(url) => coverImage = url"
						></file-upload>
					</div>
				</div>

				<div class="field-row">
					<label class="field-label">简介内容</label>
					<div class="field-control">
						<div
							v-for="(item, index) in contentList"
							:key="index"
							class="content-item"
						>
							<div class="item-top">
								<div class="item-btns">
									<button v-if="index > 0" class="btn-sm" @click="moveUp(index)">↑</button>
									<button v-if="index < contentList.length - 1" class="btn-sm" @click="moveDown(index)">↓</button>
									<button v-if="contentList.length > 1" class="btn-sm btn-del" @click="removeItem(index)">×</button>
								</div>
							</div>
							<el-input
								v-model="item.value"
								type="textarea"
								:rows="3"
								placeholder="请输入文本内容"
							></el-input>
						</div>
						<button class="btn-add-text" @click="addText">+ 添加段落</button>
					</div>
				</div>
			</div>

			<div class="save-bar">
				<el-button type="primary" class="btn-save" @click="onSubmit">保存</el-button>
			</div>

			<!-- 底部预览区 -->
			<div class="preview-section">
				<div class="preview-title">预览效果</div>
				<div class="preview-card">
					<img
						v-if="coverImage"
						class="preview-cover"
						:src="coverImage.substring(0,4)=='http' ? coverImage : $base.url + coverImage"
					/>
					<div class="preview-body">
						<div
							v-for="(item, index) in contentList"
							:key="index"
							class="preview-text"
						>{{ item.value }}</div>
						<div v-if="!coverImage && contentList.every(i => !i.value)" class="preview-empty">
							暂无内容，请在上方编辑后保存
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	data() {
		return {
			coverImage: '',
			contentList: [{ value: '' }]
		};
	},
	props: ["parent"],
	created() {
		this.loadData();
	},
	methods: {
		loadData() {
			this.$http({
				url: `xiaoyuanjianjie/info/21`,
				method: "get"
			}).then(({ data }) => {
				if (data && data.code === 0 && data.data) {
					this.coverImage = data.data.fengmian || '';
					var text = data.data.xuexiaojianjie || '';
					var parts = text ? text.split('\n---\n') : [''];
					this.contentList = parts.map(function(t) { return { value: t }; });
				}
			});
		},
		addText() {
			this.contentList.push({ value: '' });
		},
		removeItem(index) {
			this.contentList.splice(index, 1);
		},
		moveUp(index) {
			if (index <= 0) return;
			var arr = this.contentList;
			var tmp = arr[index];
			this.$set(arr, index, arr[index - 1]);
			this.$set(arr, index - 1, tmp);
		},
		moveDown(index) {
			var arr = this.contentList;
			if (index >= arr.length - 1) return;
			var tmp = arr[index];
			this.$set(arr, index, arr[index + 1]);
			this.$set(arr, index + 1, tmp);
		},
		onSubmit() {
			var url = this.coverImage ? this.coverImage.replace(new RegExp(this.$base.url, "g"), "") : '';
			var textParts = this.contentList
				.map(function(i) { return i.value; })
				.filter(function(v) { return v; });

			this.$http({
				url: `xiaoyuanjianjie/update`,
				method: "post",
				data: {
					id: 21,
					fengmian: url,
					xuexiaojianjie: textParts.join('\n---\n')
				}
			}).then(({ data }) => {
				if (data && data.code === 0) {
					this.$message({ message: "保存成功", type: "success", duration: 1500 });
				} else {
					this.$message.error(data.msg);
				}
			});
		}
	}
};
</script>

<style lang="scss" scoped>
	.editor-wrap {
		max-width: 720px;
		margin: 0 auto;
		background: #fff;
	}

	.editor-title {
		margin: 0 0 24px;
		color: #333;
		font-size: 20px;
		font-weight: 600;
		text-align: center;
	}

	/* 编辑区 */
	.edit-section {
		display: flex;
		flex-direction: column;
		gap: 20px;
	}

	.field-row {
		display: flex;
		gap: 16px;
		align-items: flex-start;
	}

	.field-label {
		flex-shrink: 0;
		width: 80px;
		padding-top: 10px;
		color: #333;
		font-size: 14px;
		font-weight: 600;
		text-align: right;
	}

	.field-control {
		flex: 1;
		min-width: 0;
	}

	.cover-preview-inline {
		display: flex;
		align-items: flex-end;
		gap: 12px;
		img {
			width: 160px;
			height: 120px;
			object-fit: cover;
			border-radius: 8px;
			border: 1px solid #eee;
		}
	}

	.btn-remove {
		padding: 4px 14px;
		border: 1px solid #f5c6cb;
		border-radius: 4px;
		background: #fff;
		color: #e74c3c;
		font-size: 12px;
		cursor: pointer;
		&:hover { background: #fdf0f0; }
	}

	.content-item {
		margin-bottom: 12px;
		border: 1px solid #e8e8e8;
		border-radius: 8px;
		overflow: hidden;
	}

	.item-top {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 6px 12px;
		background: #fafafa;
		border-bottom: 1px solid #eee;
	}

	.item-num {
		color: #999;
		font-size: 12px;
		font-weight: 600;
	}

	.item-btns {
		display: flex;
		gap: 4px;
	}

	.btn-sm {
		padding: 2px 8px;
		border: 1px solid #ddd;
		border-radius: 3px;
		background: #fff;
		color: #666;
		font-size: 12px;
		cursor: pointer;
		&:hover { border-color: #458C8C; color: #458C8C; }
	}

	.btn-del {
		color: #e74c3c;
		border-color: #f5c6cb;
		&:hover { background: #fdf0f0; border-color: #e74c3c; }
	}

	.btn-add-text {
		display: block;
		width: 100%;
		padding: 10px;
		border: 2px dashed #ddd;
		border-radius: 8px;
		background: #fafafa;
		color: #999;
		font-size: 14px;
		cursor: pointer;
		transition: all 0.2s;
		&:hover { border-color: #458C8C; color: #458C8C; background: #f0fafa; }
	}

	.save-bar {
		margin: 24px 0;
		text-align: center;
	}

	.btn-save {
		width: 160px;
		height: 44px !important;
		border: none !important;
		border-radius: 8px !important;
		font-size: 16px !important;
		font-weight: 600 !important;
		background: #458C8C !important;
	}

	/* 预览区 */
	.preview-section {
		border-top: 2px dashed #e0e0e0;
		padding-top: 24px;
	}

	.preview-title {
		color: #999;
		font-size: 13px;
		font-weight: 600;
		text-align: center;
		margin-bottom: 16px;
		letter-spacing: 0.1em;
	}

	.preview-card {
		border: 1px solid #eee;
		border-radius: 12px;
		overflow: hidden;
		background: #fff;
		box-shadow: 0 2px 12px rgba(0,0,0,0.06);
	}

	.preview-cover {
		width: 100%;
		max-height: 360px;
		object-fit: cover;
		display: block;
	}

	.preview-body {
		padding: 20px 24px 24px;
	}

	.preview-text {
		color: #333;
		font-size: 15px;
		line-height: 1.8;
		margin-bottom: 12px;
		white-space: pre-wrap;
		word-break: break-all;
		&:last-child { margin-bottom: 0; }
	}

	.preview-empty {
		color: #ccc;
		font-size: 14px;
		text-align: center;
		padding: 32px 0;
	}

	/* 覆盖全局 */
	.addEdit-block {
		background: #fff !important;
		box-shadow: none !important;
		backdrop-filter: none !important;
		border: none !important;
		padding: 0 !important;
	}
	.addEdit-block::before { display: none !important; }
</style>
