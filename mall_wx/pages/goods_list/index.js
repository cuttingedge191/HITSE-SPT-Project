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
    imageBaseURL: app.enabledUrl + '/goodsImages/',
    goods: [],
    goods_all: [],
  },

  addCart: function (e) {
    var idList = wx.getStorageSync('ids');
    var numList;
    if (idList) {
      numList = wx.getStorageSync('nums');
    } else {
      idList = [];
      numList = [];
    }
    var index = idList.indexOf(e.currentTarget.id);
    if (index < 0) {
      idList.push(e.currentTarget.id);
      numList.push(1); // 默认添加一个
    } else {
      numList[index] += 1; // 再次添加数量加一
    }
    wx.setStorageSync('ids', idList);
    wx.setStorageSync('nums', numList);
    Toast.success('已加入购物车！');
  },

  onSearch: function (e) {
    var that = this;
    var showGoods = [];
    for (let index = 0; index < that.data.goods_all.length - 1; ++index) {
      if (that.data.goods_all[index].name.includes(e.detail) || that.data.goods_all[index].description.includes(e.detail)) {
        showGoods.push(that.data.goods_all[index]);
      }
    }
    that.setData({
      goods: showGoods,
    })
  },

  cancelSearch: function () {
    var that = this;
    that.setData({
      goods: that.data.goods_all,
    })
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
          goods_all: res.data,
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