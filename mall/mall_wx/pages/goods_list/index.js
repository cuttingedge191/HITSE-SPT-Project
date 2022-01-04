// index.js
// 获取应用实例
import Page from '../../libs/common/page';
import Toast from '../../libs/dist/toast/toast';

const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    c_type: '',
    show: false,
    imageURL: "https://img.yzcdn.cn/vant/ipad.jpeg",
    goods: []
  },

  addCart: function () {
    Toast.success('已加入购物车');
  },

  viewDetail: function () {
    wx.redirectTo({
      url: '/pages/goods_detail/index'
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function () {
    var that = this;
    var cType = wx.getStorageSync('c_type');
    wx.request({
      url: app.enabledUrl + '/mall/getGoodsList',
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        that.setData({
          goods: res.data,
          c_type: cType
        })
      }
    });
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