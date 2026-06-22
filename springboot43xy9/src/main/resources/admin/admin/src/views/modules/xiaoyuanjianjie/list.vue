<template>
  <div class="main-content">
    <div class="campus-intro">
      <div class="intro-header">
        <h2>校园简介</h2>
        <p>编辑校园介绍信息（ID: 21），支持文字、图片、附件</p>
      </div>

      <div class="intro-content">
        <el-input
          ref="editor"
          v-model="introText"
          type="textarea"
          :rows="18"
          placeholder="请输入校园简介内容..."
          :style="{
            'fontSize': '14px',
            'lineHeight': '1.8',
            'borderRadius': '12px',
            'border': '2px solid #e0e0e0',
            'padding': '16px'
          }"
        ></el-input>
      </div>

      <!-- 图片上传区域 -->
      <div class="upload-section">
        <div class="section-title">图片上传</div>
        <el-upload
          class="upload-area"
          action="/SchoolMap/file/upload"
          :on-success="handleImageSuccess"
          :before-upload="beforeUpload"
          :show-file-list="false"
          accept="image/*"
        >
          <el-button size="small" type="primary">点击上传图片</el-button>
          <div slot="tip" class="el-upload__tip">支持 jpg/png/gif 格式，上传后会自动插入到文本框</div>
        </el-upload>
      </div>

      <!-- 附件上传区域 -->
      <div class="upload-section">
        <div class="section-title">附件上传</div>
        <el-upload
          class="upload-area"
          action="/SchoolMap/file/upload"
          :on-success="handleFileSuccess"
          :before-upload="beforeUpload"
          :show-file-list="false"
        >
          <el-button size="small" type="success">点击上传附件</el-button>
          <div slot="tip" class="el-upload__tip">支持 pdf/doc/docx/zip 等格式</div>
        </el-upload>
      </div>

      <div class="intro-actions">
        <el-button
          type="primary"
          @click="saveIntro"
          :loading="saving"
          :style="{
            'border': '0',
            'borderRadius': '8px',
            'padding': '0 32px',
            'height': '44px',
            'fontSize': '16px',
            'fontWeight': '600',
            'background': 'linear-gradient(135deg, #2f7a69 0%, #214f46 100%)',
            'boxShadow': '0 4px 12px rgba(47, 122, 105, 0.3)'
          }"
        >
          保存
        </el-button>
        <el-button
          @click="resetIntro"
          :style="{
            'borderRadius': '8px',
            'padding': '0 32px',
            'height': '44px',
            'fontSize': '16px'
          }"
        >
          重置
        </el-button>
      </div>

      <div class="intro-preview" v-if="introText">
        <div class="preview-header">预览效果</div>
        <div class="preview-content" v-html="introText"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      introText: '',
      originalText: '',
      saving: false
    };
  },
  created() {
    this.getIntro();
  },
  methods: {
    getIntro() {
      this.$http({
        url: 'xiaoyuanjianjie/detail/21',
        method: 'get'
      }).then(({ data }) => {
        if (data && data.code === 0 && data.data) {
          this.introText = data.data.xuexiaojianjie || '';
          this.originalText = this.introText;
        }
      });
    },
    saveIntro() {
      if (!this.introText.trim()) {
        this.$message.warning('请输入校园简介内容');
        return;
      }
      this.saving = true;
      // 先获取完整数据
      this.$http({
        url: 'xiaoyuanjianjie/detail/21',
        method: 'get'
      }).then(({ data }) => {
        if (data && data.code === 0 && data.data) {
          const fullData = data.data;
          fullData.xuexiaojianjie = this.introText;
          // 使用完整数据更新
          return this.$http({
            url: 'xiaoyuanjianjie/updateIntro',
            method: 'post',
            data: fullData
          });
        } else {
          throw new Error('获取数据失败');
        }
      }).then(({ data }) => {
        this.saving = false;
        if (data && data.code === 0) {
          this.$message.success('保存成功');
          this.originalText = this.introText;
        } else {
          this.$message.error(data.msg || '保存失败');
        }
      }).catch(() => {
        this.saving = false;
        this.$message.error('保存失败');
      });
    },
    resetIntro() {
      this.introText = this.originalText;
      this.$message.info('已重置');
    },
    beforeUpload(file) {
      const isLt10M = file.size / 1024 / 1024 < 10;
      if (!isLt10M) {
        this.$message.error('文件大小不能超过 10MB');
      }
      return isLt10M;
    },
    handleImageSuccess(response) {
      if (response.code === 0) {
        const imageUrl = 'http://localhost:8080/SchoolMap/upload/' + response.file;
        const imgHtml = `<img src="${imageUrl}" style="max-width:100%;border-radius:8px;margin:8px 0;" />`;
        this.introText += imgHtml;
        this.$message.success('图片上传成功');
      } else {
        this.$message.error('图片上传失败');
      }
    },
    handleFileSuccess(response) {
      if (response.code === 0) {
        const fileUrl = 'http://localhost:8080/SchoolMap/upload/' + response.file;
        const fileName = response.file;
        const linkHtml = `<p><a href="${fileUrl}" target="_blank" style="color:#2f7a69;">[附件] ${fileName}</a></p>`;
        this.introText += linkHtml;
        this.$message.success('附件上传成功');
      } else {
        this.$message.error('附件上传失败');
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.campus-intro {
  max-width: 900px;
  margin: 0 auto;
  padding: 32px;
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

.intro-header {
  margin-bottom: 28px;

  h2 {
    margin: 0 0 8px;
    font-size: 24px;
    color: #203630;
    font-weight: 700;
  }

  p {
    margin: 0;
    color: #6a7f78;
    font-size: 14px;
  }
}

.intro-content {
  margin-bottom: 24px;
}

.upload-section {
  margin-bottom: 20px;
  padding: 16px;
  background: #f8fafa;
  border-radius: 12px;

  .section-title {
    margin-bottom: 12px;
    color: #203630;
    font-size: 14px;
    font-weight: 600;
  }
}

.intro-actions {
  display: flex;
  gap: 12px;
  margin-bottom: 32px;
}

.intro-preview {
  border-top: 1px solid #eee;
  padding-top: 24px;

  .preview-header {
    margin-bottom: 12px;
    color: #6a7f78;
    font-size: 14px;
    font-weight: 600;
  }

  .preview-content {
    padding: 20px;
    background: #f8fafa;
    border-radius: 12px;
    color: #333;
    font-size: 15px;
    line-height: 1.8;
    min-height: 100px;

    img {
      max-width: 100%;
      height: auto;
      border-radius: 8px;
      margin: 8px 0;
    }

    a {
      color: #2f7a69;
      text-decoration: underline;
    }
  }
}
</style>
