// pages/goods_detail/index.js
import Page from '../../libs/common/page';
import Toast from '../../libs/dist/toast/toast';

Page({
  /**
   * 页面的初始数据
   */
  data: {
    src: 'https://img.yzcdn.cn/vant/cat.jpeg'
  },

  onClickButton: function() {
    Toast.success('已加入购物车');
  },

  onClickCart: function() {
    wx.switchTab({
      url: '/pages/cart/index'
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // wx.navigateBack({
    //   delta: 1
    // })
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