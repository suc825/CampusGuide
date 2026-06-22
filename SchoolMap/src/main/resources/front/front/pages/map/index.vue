<template>
	<view class="content">
		<input type="text" style="width: 100%;text-align: center;background: #eee;color: #000;" v-model="key" @input="search" placeholder="地址搜索" />
		<scroll-view scroll-y="true" class="option" v-show="IsOption">
			<view class='column_item' v-for='(item,index) in data' :key='index' @click="tapOption(item)">{{item.title}}
			</view>
		</scroll-view>
		<map v-show="!IsOption" id="map" style="width: 100%; height: 450px;z-index: -1;" :latitude="latitude" :longitude="longitude" :markers="markers"
			 show-location></map>
		<button type="primary" class="btn" @click="getNavigationRoute">导航</button>
	</view>
</template>

<script>
	var AMapWX = require('../../common/amap-wx.js');
	var amapsdk;
	var self;
	export default {
		data() {
			return {
				keyword: '',
				data: [],
				latitude: 23.099994,
				longitude: 113.324520,
				IsOption: false,
				key: '',
				markers: [{
					id: 1,
					latitude: 23.099994,
					longitude: 113.324520,
					width:18,
					height:22,
					name: 'T.I.T 创意园',
					iconPath: '../../static/location.png',
				}],
			}
		},
		onLoad() {
			self = this
			// 实例化API核心类
			amapsdk = new AMapWX({
				key: '06952b5ac572c9b2f557a46b5d1540a8'
			});
			uni.getLocation({
				altitude: true,
				success(res) {
					self.longitude = res.longitude
					self.latitude = res.latitude
				}
			})
			self.mapCtr = uni.createMapContext('map')
		},
		methods: {
			search() {
				// #ifdef H5
				const KEY = "06952b5ac572c9b2f557a46b5d1540a8";
				let keyword = self.key;
				if (!keyword) {
					uni.showToast({
						title: '请输入搜索关键词',
						icon: 'none'
					});
					return;
				}
				
				// 使用uni.request代替jsonp，避免跨域问题
				uni.request({
					url: 'https://restapi.amap.com/v3/place/text',
					data: {
						key: KEY,
						keywords: keyword,
						location: self.longitude + ',' + self.latitude,
						radius: 1000,
						offset: 20,
						output: 'json'
					},
					method: 'GET',
					success: function(res) {
						console.log('搜索结果:', res.data);
						if (res.data.status === '1') {
							self.data = res.data.pois.map(item => ({
								title: item.name,
								location: {
									lat: parseFloat(item.location.split(',')[1]),
									lng: parseFloat(item.location.split(',')[0])
								}
							}));
							if (self.key && self.data.length) {
								self.IsOption = true
							} else {
								self.IsOption = false
							}
						} else {
							console.log('搜索失败:', res.data.info);
							uni.showToast({
								title: '搜索失败: ' + res.data.info,
								icon: 'none'
							});
						}
					},
					fail: function(err) {
						console.log('请求错误:', err);
						uni.showToast({
							title: '网络请求失败，请检查网络连接',
							icon: 'none'
						});
					}
				});
				return false
				// #endif
				var location = self.longitude + "," + self.latitude 
				self.mapSearch(self.key, location).then(res1 => {
					console.log('地图搜索结果:', res1);
					if (res1.status === '1') {
						self.data = res1.pois.map(item => ({
							title: item.name,
							location: {
								lat: parseFloat(item.location.split(',')[1]),
								lng: parseFloat(item.location.split(',')[0])
							}
						}));
						if (self.key && self.data.length) {
							self.IsOption = true
						} else {
							self.IsOption = false
						}
					} else {
						console.log('搜索失败:', res1.info);
						uni.showToast({
							title: '搜索失败: ' + res1.info,
							icon: 'none'
						});
					}
				}, error => {
					console.log('地图搜索错误:', error);
					uni.showToast({
						title: '搜索失败',
						icon: 'none'
					});
				})
			},
			tapOption: function(item) {
				self.latitude = item.location.lat;
				self.longitude = item.location.lng;
				self.markers[0].latitude = item.location.lat;
				self.markers[0].longitude = item.location.lng;
				self.searchKey = item.title
				self.IsOption = false
				self.data = []
				var obj = {}
				obj.latitude = item.location.lat;
				obj.longitude = item.location.lng;
				self.toLocation(obj)
			},
			toLocation: function(obj) {
				self.mapCtr.moveToLocation(obj)
				self.mapCtr.translateMarker({
					markerId: 1,
					autoRotate: true,
					duration: 100,
					destination: {
						latitude: obj.latitude,
						longitude: obj.longitude,
					},
					animationEnd() {
						// console.log('animation end')
					}
				})
			},
			openMapApp() {
				let latitude = self.latitude;
				let longitude = self.longitude;

				uni.openLocation({
					latitude: latitude,
					longitude: longitude,
					name: self.searchKey || '目的地',
					address: '',
					success: function(res) {
						// console.log(res);
					}
				});
			},
			getNavigationRoute() {
				// 获取当前位置
				uni.getLocation({
					type: 'gcj02',
					success: function(res) {
						let origin = res.longitude + ',' + res.latitude;
						let destination = self.longitude + ',' + self.latitude;
						
						// 调用高德地图路径规划API
						amapsdk.getDrivingRoute({
							origin: origin,
							destination: destination,
							success: function(res) {
								console.log('导航路线规划成功:', res);
								// 可以在这里处理导航路线数据
								uni.showToast({
									title: '导航路线规划成功',
									icon: 'success'
								});
								// 打开系统地图进行导航
								self.openMapApp();
							},
							fail: function(res) {
								console.log('导航路线规划失败:', res);
								uni.showToast({
									title: '导航路线规划失败',
									icon: 'none'
								});
							}
						});
					},
					fail: function(res) {
						console.log('获取位置失败:', res);
						uni.showToast({
							title: '获取位置失败',
							icon: 'none'
						});
					}
				});
			},
			mapSearch(keyword, location) {
				let promise = new Promise(function(resolve, reject) {
					amapsdk.search({
						keyword: keyword,
						location: location,
						success: function(res) {
							resolve(res)
						},
						fail: function(res) {
							reject(res)
						}
					});
				})
				return promise
			},
		}
	}
</script>

<style>
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}

	.option {
		max-height: 300rpx;
		width: 100%;

		line-height: 60rpx;
		position: fixed;
		/* #ifdef H5 */
		top: 188rpx;
		/* #endif */
		/* #ifdef MP-WEIXIN */
		top: 42rpx;
		/* #endif */
		/* #ifdef APP-VUE */
		top: 98rpx;
		/* #endif */
		z-index: 9999;
	}

	.column_item {
		padding: 0 40rpx;
		height: 60rpx;
		width: 524rpx;
		overflow: hidden;
		margin: 0rpx auto;
		background-color: #fff;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.column_item:active {
		background-color: #8F8F94;
	}
	.btn{
		position: fixed;
		bottom:  var(--window-bottom);
		left: 0;
		width: 100%;
		height: 88rpx;
		border-radius: 0;
	}
</style>
