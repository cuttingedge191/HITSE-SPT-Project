// pages/user/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    name:"",
    account:"",
    fits: [
      'cover'
    ],
    src: "https://img.yzcdn.cn/vant/cat.jpeg"
  },

  changeInfo: function() {
    wx.navigateTo({
      url: '/pages/userInfo/index',
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var userinfo = wx.getStorageSync('userinfo'); //获取本地缓存中的userinfo
    console.log("本地用户信息",userinfo);
    this.setData({
      account : userinfo.value.customer_id
    })
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
