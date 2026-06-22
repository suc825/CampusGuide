Page({
  data: {
    introText: '',
    fileList: [],
    loading: true
  },

  onLoad() {
    this.getIntro()
  },

  onShow() {
    this.getIntro()
  },

  getIntro() {
    this.setData({ loading: true })

    wx.request({
      url: 'http://192.168.154.1:8080/SchoolMap/xiaoyuanjianjie/detail/21',
      method: 'GET',
      success: (res) => {
        console.log('API响应:', res.data)
        if (res.data && res.data.code === 0 && res.data.data) {
          var content = res.data.data.xuexiaojianjie || ''
          // 提取附件链接
          var fileList = []
          var reg = /<a[^>]*href="([^"]*)"[^>]*>([^<]*)<\/a>/g
          var match
          while ((match = reg.exec(content)) !== null) {
            var url = match[1]
            var name = match[2].replace('[附件] ', '')
            // 补全相对路径
            if (url.indexOf('/SchoolMap/') === 0) {
              url = 'http://192.168.154.1:8080' + url
            }
            fileList.push({ url: url, name: name })
          }
          // 移除<a>标签，rich-text不支持
          content = content.replace(/<p><a[^>]*>.*?<\/a><\/p>/g, '')
          // 补全图片src为完整URL
          content = content.replace(/src="\/SchoolMap\//g, 'src="http://192.168.154.1:8080/SchoolMap/')
          this.setData({
            introText: content,
            fileList: fileList,
            loading: false
          })
        } else {
          this.setData({ introText: '', fileList: [], loading: false })
        }
      },
      fail: (err) => {
        console.error('请求失败:', err)
        this.setData({ introText: '', fileList: [], loading: false })
      }
    })
  },

  openFile: function(e) {
    var url = e.currentTarget.dataset.url
    var name = e.currentTarget.dataset.name
    var ext = name.split('.').pop().toLowerCase()
    if (!ext || ext === name.toLowerCase()) {
      ext = url.split('.').pop().toLowerCase().split('?')[0]
    }

    // 图片类型直接预览
    var imageTypes = ['jpg', 'jpeg', 'png', 'gif', 'bmp']
    if (imageTypes.indexOf(ext) >= 0) {
      wx.previewImage({ urls: [url] })
      return
    }

    // txt等文本文件：复制链接到浏览器打开
    var textTypes = ['txt', 'csv', 'json', 'xml', 'html', 'log']
    if (textTypes.indexOf(ext) >= 0) {
      wx.showModal({
        title: '提示',
        content: '该文件需在浏览器中打开，已复制链接，请粘贴到浏览器地址栏',
        showCancel: false,
        success: function() {
          wx.setClipboardData({ data: url })
        }
      })
      return
    }

    // wx.openDocument 支持的文件类型
    var supportedTypes = ['doc', 'docx', 'xls', 'xlsx', 'ppt', 'pptx', 'pdf']
    if (supportedTypes.indexOf(ext) < 0) {
      wx.showModal({
        title: '提示',
        content: '该格式文件需在浏览器中打开，是否复制链接？',
        confirmText: '复制链接',
        success: function(res) {
          if (res.confirm) {
            wx.setClipboardData({ data: url })
          }
        }
      })
      return
    }

    wx.showLoading({ title: '打开中...' })

    wx.downloadFile({
      url: url,
      success: function(res) {
        wx.hideLoading()
        if (res.statusCode === 200) {
          wx.openDocument({
            filePath: res.tempFilePath,
            fileType: ext,
            showMenu: true,
            fail: function(err) {
              console.error('打开文件失败:', err)
              wx.showToast({ title: '打开失败', icon: 'none' })
            }
          })
        } else {
          wx.showToast({ title: '下载失败', icon: 'none' })
        }
      },
      fail: function(err) {
        wx.hideLoading()
        console.error('下载失败:', err)
        wx.showToast({ title: '下载失败', icon: 'none' })
      }
    })
  }
})
