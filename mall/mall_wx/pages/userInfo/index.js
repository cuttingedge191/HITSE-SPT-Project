// pages/logup/index.js

const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    gender: '1',
    password: '',
    ackpassword:'',
    username: '测试',
    phone: '1383838383838',
    addr: 'false',
    checkpw:'',
    src:"https://img.yzcdn.cn/vant/cat.jpeg"
  },

  //选择性别
  onChange(event) {
    this.setData({
      radio: event.detail,
    });
  },
 //注册
 changeInfo: function() {
     wx.switchTab({
      url: '/pages/user/index'
    })
  },
   // 上传头像
   changeImage: function () {
    var that = this;
    wx.chooseImage({
        count: 1, // 默认9
        sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
        success: function (res) {
            // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片，只有一张图片获取下标为0
            var tempFilePaths = res.tempFilePaths[0];
            that.setData({
                src: tempFilePaths,
                actionSheetHidden: !that.data.actionSheetHidden
            })
            util.uploadFile('/itdragon/uploadImage', tempFilePaths, 'imgFile' ,{}, function (res) {
                console.log(res);
                if (null != res) {
                    that.setData({
                        userImg: res
                    })
                } else {
                    // 显示消息提示框
                    wx.showToast({
                        title: '上传失败',
                        icon: 'error',
                        duration: 2000
                    })
                }
            });
        }
    })    
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
        
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})