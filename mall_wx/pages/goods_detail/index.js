// pages/goods_detail/index.js
import Page from '../../libs/common/page';
import Toast from '../../libs/dist/toast/toast';

const app = getApp();

Page({
  data: {
    c_type: '',
    goodInfo: {},
    imageBaseURL: app.enabledUrl + '/goodsImages/',
    num: 1,
  },

  onClickButton: function () {
    Toast.success('已加入购物车');
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
    var index = idList.indexOf(this.data.goodInfo.g_id.toString());
    if (index < 0) {
      idList.push(this.data.goodInfo.g_id.toString());
      numList.push(this.data.num); // 添加 num 个
    } else {
      numList[index] += this.data.num; // 再次添加 num 个
    }
    wx.setStorageSync('ids', idList);
    wx.setStorageSync('nums', numList);
    Toast.success('已加入购物车！');
    setTimeout(function () {
      wx.switchTab({
        url: '/pages/goods_list/index'
      });
    }, 1000); // 延迟1秒再跳转
  },

  onClickCart: function () {
    wx.switchTab({
      url: '/pages/cart/index'
    });
  },

  ChangeGoodNum: function (e) {
    this.setData({
      num: e.detail,
    })
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