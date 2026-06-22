/**
 * 高德地图微信小程序SDK
 * 用于微信小程序中调用高德地图相关服务
 */

class AMapWX {
  constructor(options) {
    this.key = options.key;
    this.securityJsCode = options.securityJsCode || '';
  }

  /**
   * 地理编码
   * @param {Object} params - 参数
   * @param {Function} params.success - 成功回调
   * @param {Function} params.fail - 失败回调
   */
  getGeocoder(params) {
    const url = 'https://restapi.amap.com/v3/geocode/geo';
    const data = {
      key: this.key,
      address: params.address,
      city: params.city || ''
    };
    this.request(url, data, params.success, params.fail);
  }

  /**
   * 逆地理编码
   * @param {Object} params - 参数
   * @param {Number} params.location - 经纬度，格式："经度,纬度"
   * @param {Function} params.success - 成功回调
   * @param {Function} params.fail - 失败回调
   */
  getRegeo(params) {
    const url = 'https://restapi.amap.com/v3/geocode/regeo';
    const data = {
      key: this.key,
      location: params.location,
      radius: params.radius || 1000,
      extensions: params.extensions || 'base'
    };
    this.request(url, data, params.success, params.fail);
  }

  /**
   * 搜索POI
   * @param {Object} params - 参数
   * @param {String} params.keywords - 搜索关键词
   * @param {String} params.location - 经纬度，格式："经度,纬度"
   * @param {Function} params.success - 成功回调
   * @param {Function} params.fail - 失败回调
   */
  search(params) {
    const url = 'https://restapi.amap.com/v3/place/text';
    const data = {
      key: this.key,
      keywords: params.keyword,
      location: params.location,
      radius: params.radius || 1000,
      page: params.page || 1,
      offset: params.offset || 20
    };
    this.request(url, data, params.success, params.fail);
  }

  /**
   * 路径规划
   * @param {Object} params - 参数
   * @param {String} params.origin - 起点经纬度，格式："经度,纬度"
   * @param {String} params.destination - 终点经纬度，格式："经度,纬度"
   * @param {String} params.mode - 导航模式，可选：driving（驾车）、walking（步行）、bicycling（骑行）
   * @param {Function} params.success - 成功回调
   * @param {Function} params.fail - 失败回调
   */
  getDrivingRoute(params) {
    const url = 'https://restapi.amap.com/v3/direction/driving';
    const data = {
      key: this.key,
      origin: params.origin,
      destination: params.destination,
      strategy: params.strategy || 0
    };
    this.request(url, data, params.success, params.fail);
  }

  /**
   * 发送请求
   * @param {String} url - 请求地址
   * @param {Object} data - 请求参数
   * @param {Function} success - 成功回调
   * @param {Function} fail - 失败回调
   */
  request(url, data, success, fail) {
    wx.request({
      url: url,
      data: data,
      method: 'GET',
      success: function(res) {
        if (res.data.status === '1') {
          success(res.data);
        } else {
          fail(res.data);
        }
      },
      fail: function(res) {
        fail(res);
      }
    });
  }
}

export default AMapWX;