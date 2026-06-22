Page({
  data: {
    latitude: 28.13293,
    longitude: 112.99653,
    currentLatitude: null,
    currentLongitude: null,
    destinationLatitude: null,
    destinationLongitude: null,
    destinationName: '',
    destinationAddress: '',
    key: '',
    suggestions: [],
    IsOption: false,
    routeInfo: {
      modeText: '',
      distanceText: '',
      durationText: '',
      noteText: ''
    },
    polyline: [],
    includePoints: [],
    markers: [{
      id: 1,
      latitude: 28.13293,
      longitude: 112.99653,
      width: 18,
      height: 22,
      title: '当前位置',
      name: '当前位置',
      iconPath: '../../static/location.png'
    }]
  },

  onLoad: function() {
    this.locateCurrentPosition(true);
  },

  onUnload: function() {
    if (this._searchTimer) {
      clearTimeout(this._searchTimer);
      this._searchTimer = null;
    }
  },

  locateCurrentPosition: function(showError) {
    var that = this;
    wx.getLocation({
      type: 'gcj02',
      isHighAccuracy: true,
      altitude: true,
      success: function(res) {
        var lat = Number(res.latitude);
        var lng = Number(res.longitude);
        that.currentLatitude = lat;
        that.currentLongitude = lng;
        that.setData({
          currentLatitude: lat,
          currentLongitude: lng
        });
        that.updateMarkers();
        that.updateMapCenter(lat, lng);
      },
      fail: function(err) {
        if (showError) {
          that.handleLocationFail(err);
        }
      }
    });
  },

  handleLocationFail: function(err) {
    var that = this;
    var msg = (err && err.errMsg) || '';
    if (/auth deny|auth denied|scope.userLocation/i.test(msg)) {
      wx.showModal({
        title: '需要位置权限',
        content: '请允许小程序获取位置信息后再使用定位和地图选点。',
        confirmText: '去设置',
        success: function(res) {
          if (res.confirm) {
            wx.openSetting({
              success: function(settingRes) {
                if (settingRes.authSetting && settingRes.authSetting['scope.userLocation']) {
                  that.locateCurrentPosition(false);
                }
              }
            });
          }
        }
      });
      return;
    }
    wx.showToast({ title: '定位失败，已返回默认位置', icon: 'none' });
    that.updateMapCenter(28.13293, 112.99653);
  },

  updateMarkers: function() {
    var markers = [];
    if (this.currentLatitude != null && this.currentLongitude != null) {
      markers.push({
        id: 1,
        latitude: Number(this.currentLatitude),
        longitude: Number(this.currentLongitude),
        width: 18,
        height: 22,
        title: '我的位置',
        name: '我的位置',
        iconPath: '../../static/location.png'
      });
    }
    if (this.destinationLatitude != null && this.destinationLongitude != null) {
      markers.push({
        id: 2,
        latitude: Number(this.destinationLatitude),
        longitude: Number(this.destinationLongitude),
        width: 18,
        height: 22,
        title: this.destinationName || '目的地',
        name: this.destinationName || '目的地',
        iconPath: '../../static/location.png'
      });
    }
    if (!markers.length) {
      markers.push({
        id: 1,
        latitude: Number(this.data.latitude),
        longitude: Number(this.data.longitude),
        width: 18,
        height: 22,
        title: '当前位置',
        name: '当前位置',
        iconPath: '../../static/location.png'
      });
    }
    this.setData({ markers: markers });
  },

  updateMapCenter: function(lat, lng) {
    this.setData({
      latitude: Number(lat),
      longitude: Number(lng)
    });
  },

  calcDistance: function(lat1, lng1, lat2, lng2) {
    var rad = Math.PI / 180;
    var R = 6371000;
    var dLat = (lat2 - lat1) * rad;
    var dLng = (lng2 - lng1) * rad;
    var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(lat1 * rad) * Math.cos(lat2 * rad) *
      Math.sin(dLng / 2) * Math.sin(dLng / 2);
    return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  },

  formatDistance: function(meters) {
    var d = Number(meters || 0);
    return d >= 1000 ? (d / 1000).toFixed(2) + ' km' : Math.round(d) + ' m';
  },

  formatDuration: function(seconds) {
    var s = Number(seconds || 0);
    var min = Math.round(s / 60);
    if (min >= 60) return Math.floor(min / 60) + '小时' + (min % 60) + '分钟';
    return Math.max(min, 1) + '分钟';
  },

  showRoute: function() {
    if (this.currentLatitude == null || this.currentLongitude == null ||
      this.destinationLatitude == null || this.destinationLongitude == null) {
      return;
    }
    var from = { latitude: Number(this.currentLatitude), longitude: Number(this.currentLongitude) };
    var to = { latitude: Number(this.destinationLatitude), longitude: Number(this.destinationLongitude) };
    var dist = this.calcDistance(from.latitude, from.longitude, to.latitude, to.longitude);
    this.setData({
      polyline: [{
        points: [from, to],
        color: '#14b37d',
        width: 4,
        dottedLine: true
      }],
      includePoints: [from, to],
      routeInfo: {
        modeText: '步行路线',
        distanceText: this.formatDistance(dist),
        durationText: this.formatDuration(dist / 0.8),
        noteText: ''
      }
    });
  },

  clearRoute: function() {
    this.setData({
      polyline: [],
      includePoints: [],
      routeInfo: { modeText: '', distanceText: '', durationText: '', noteText: '' }
    });
  },

  setDestination: function(item) {
    if (!item) return;
    var lat = Number(item.latitude != null ? item.latitude : item.location && item.location.lat);
    var lng = Number(item.longitude != null ? item.longitude : item.location && item.location.lng);
    if (isNaN(lat) || isNaN(lng)) {
      wx.showToast({ title: '地点坐标无效', icon: 'none' });
      return;
    }
    this.destinationLatitude = lat;
    this.destinationLongitude = lng;
    this.setData({
      destinationLatitude: lat,
      destinationLongitude: lng,
      destinationName: item.title || item.name || '已选地点',
      destinationAddress: item.address || ''
    });
    this.clearRoute();
    this.updateMarkers();
    this.updateMapCenter(lat, lng);
    this.showRoute();
  },

  onSearchInput: function(e) {
    var val = e.detail.value;
    this.setData({ key: val });
    return val;
  },

  search: function() {
    this.handleChooseLocation();
  },

  onSuggestionTap: function(e) {
    var idx = e.currentTarget.dataset.index;
    var item = this.data.suggestions[idx];
    if (item) {
      this.setData({ key: item.title || item.name || this.data.key });
      this.setDestination(item);
    }
  },

  tapOption: function(e) {
    var item = this.data.suggestions[e.currentTarget.dataset.index];
    if (item) {
      this.setData({ key: item.title || item.name || this.data.key });
      this.setDestination(item);
    }
  },

  resetToCurrentLocation: function() {
    this.locateCurrentPosition(true);
  },

  handleChooseLocation: function() {
    var that = this;
    wx.chooseLocation({
      success: function(res) {
        that.setData({ key: res.name || res.address || '' });
        that.setDestination({
          title: res.name,
          address: res.address,
          latitude: res.latitude,
          longitude: res.longitude
        });
      },
      fail: function(err) {
        var msg = (err && err.errMsg) || '';
        if (!/cancel/i.test(msg)) {
          that.handleLocationFail(err);
        }
      }
    });
  }
});
