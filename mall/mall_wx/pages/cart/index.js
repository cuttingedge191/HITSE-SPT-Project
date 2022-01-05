// pages/cart/index.js
import Dialog from '../../libs/dist/dialog/dialog';
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    addr:"",
    cartItems: [],
    imageURL: 'https://img.yzcdn.cn/upload_files/2017/07/02/af5b9f44deaeb68000d7e4a711160c53.jpg'
  },

  changeAddr:function() {
    wx.navigateTo({
      url: '/pages/userInfo/index'
    })
  },

  deleteCartItem:function(event) {
    const { position, instance } = event.detail;
    switch (position) {
      case 'right':
        Dialog.confirm({
          message: '确定删除吗？',
        }).then(() => {
          // 确定删除执行这里
          console.log("-------------------------");
          instance.close();
        });
        break;
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function () {
    var that = this;
    var c_id = wx.getStorageSync('c_id');
    wx.request({
      url: app.enabledUrl + '/mall/getCustomerAddressByCid?c_id=' + c_id,
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        that.setData({
          addr: res.data
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