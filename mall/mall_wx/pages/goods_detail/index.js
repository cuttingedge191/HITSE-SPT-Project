// pages/goods_detail/index.js
import Page from '../../libs/common/page';
import Toast from '../../libs/dist/toast/toast';

const app = getApp();

Page({
  data: {
    c_type: '',
    goodInfo: {},
  },

  onClickButton: function () {
    Toast.success('已加入购物车');
  },

  onClickCart: function () {
    wx.switchTab({
      url: '/pages/cart/index'
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    var cType = wx.getStorageSync('c_type');
    wx.request({
      url: app.enabledUrl + '/mall/getGoodsInfoByGid?g_id=' + options.g_id,
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        that.setData({
          goodInfo: res.data,
          c_type: cType
        })
      }
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
  onShow: function () {},


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