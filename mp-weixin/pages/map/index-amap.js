const AMapWX = require('../../common/amap-wx.js');
let amapsdk;
let self;

Page({
  data: {
    keyword: '',
    data: [],
    latitude: 23.099994,
    longitude: 113.32452,
    IsOption: false,
    key: '',
    markers: [{
      id: 1,
      latitude: 23.099994,
      longitude: 113.32452,
      width: 18,
      height: 22,
      name: 'T.I.T 创意园',
      iconPath: '../../static/location.png'
    }]
  },
  onLoad: function() {
    self = this;
    // 实例化API核心类
    amapsdk = new AMapWX({
      key: '06952b5ac572c9b2f557a46b5d1540a8'
    });
    wx.getLocation({
      altitude: true,
      success: function(res) {
        self.setData({
          longitude: res.longitude,
          latitude: res.latitude,
          'markers[0].longitude': res.longitude,
          'markers[0].latitude': res.latitude
        });
      }
    });
    self.mapCtr = wx.createMapContext('map');
  },
  methods: {
    search: function() {
      var location = self.data.latitude + ',' + self.data.longitude;
      self.mapSearch(self.data.key, location).then(res1 => {
        console.log('搜索结果:', res1);
        if (res1.status === '1') {
          let data = res1.pois.map(item => ({
            title: item.name,
            location: {
              lat: parseFloat(item.location.split(',')[1]),
              lng: parseFloat(item.location.split(',')[0])
            }
          }));
          self.setData({
            data: data,
            IsOption: self.data.key && data.length
          });
        } else {
          wx.showToast({
            title: '搜索失败: ' + res1.info,
            icon: 'none'
          });
          self.setData({
            IsOption: false
          });
        }
      }, error => {
        console.log('搜索错误:', error);
        wx.showToast({
          title: '搜索失败',
          icon: 'none'
        });
        self.setData({
          IsOption: false
        });
      });
    },
    tapOption: function(e) {
      let item = self.data.data[e.currentTarget.dataset.index];
      self.setData({
        latitude: item.location.lat,
        longitude: item.location.lng,
        'markers[0].latitude': item.location.lat,
        'markers[0].longitude': item.location.lng,
        searchKey: item.title,
        IsOption: false,
        data: []
      });
      var obj = {
        latitude: item.location.lat,
        longitude: item.location.lng
      };
      self.toLocation(obj);
    },
    toLocation: function(obj) {
      self.mapCtr.moveToLocation(obj);
      self.mapCtr.translateMarker({
        markerId: 1,
        autoRotate: true,
        duration: 100,
        destination: {
          latitude: obj.latitude,
          longitude: obj.longitude
        },
        animationEnd() {
          // console.log('animation end');
        }
      });
    },
    openMapApp: function() {
      let latitude = self.data.latitude;
      let longitude = self.data.longitude;

      wx.openLocation({
        latitude: latitude,
        longitude: longitude,
        name: self.data.searchKey || '目的地',
        address: '',
        success: function(res) {
          // console.log(res);
        }
      });
    },
    getNavigationRoute: function() {
      // 获取当前位置
      wx.getLocation({
        type: 'gcj02',
        success: function(res) {
          let origin = res.longitude + ',' + res.latitude;
          let destination = self.data.longitude + ',' + self.data.latitude;
          
          // 调用高德地图路径规划API
          amapsdk.getDrivingRoute({
            origin: origin,
            destination: destination,
            success: function(res) {
              console.log('导航路线规划成功:', res);
              wx.showToast({
                title: '导航路线规划成功',
                icon: 'success'
              });
              // 打开系统地图进行导航
              self.openMapApp();
            },
            fail: function(res) {
              console.log('导航路线规划失败:', res);
              wx.showToast({
                title: '导航路线规划失败',
                icon: 'none'
              });
            }
          });
        },
        fail: function(res) {
          console.log('获取位置失败:', res);
          wx.showToast({
            title: '获取位置失败',
            icon: 'none'
          });
        }
      });
    },
    mapSearch: function(keyword, location) {
      return new Promise(function(resolve, reject) {
        amapsdk.search({
          keyword: keyword,
          location: location,
          success: function(res) {
            resolve(res);
          },
          fail: function(res) {
            reject(res);
          }
        });
      });
    }
  }
});