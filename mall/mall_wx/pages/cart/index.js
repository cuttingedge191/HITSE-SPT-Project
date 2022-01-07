// pages/cart/index.js
import Dialog from '../../libs/dist/dialog/dialog';
import Toast from '../../libs/dist/toast/toast';

const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    addr: '',
    cart: [],
    total: 0,
    imageURL: 'https://img.yzcdn.cn/upload_files/2017/07/02/af5b9f44deaeb68000d7e4a711160c53.jpg'
  },

  changeAddr: function () {
    wx.navigateTo({
      url: '/pages/userInfo/index'
    })
  },

  deleteCartItem: function (event) {
    var that = this;
    var g_id = event.currentTarget.id.toString();
    const {
      position,
      instance
    } = event.detail;
    switch (position) {
      case 'right':
        Dialog.confirm({
          message: '确定删除吗？',
        }).then(() => {
          // 确定删除执行这里
          var idList = wx.getStorageSync('ids');
          var numList = wx.getStorageSync('nums');
          var index = idList.indexOf(g_id);
          idList.splice(index, 1)
          numList.splice(index, 1)
          wx.setStorageSync('ids', idList);
          wx.setStorageSync('nums', numList);
          let cart_tmp = that.data.cart;
          let i;
          for (i = 0; i < cart_tmp.length; ++i) {
            if (cart_tmp[i].g_id == g_id) break;
          }
          var total_tmp = that.data.total - cart_tmp[i].price * 100
          cart_tmp.splice(i,1)
          that.setData({
            cart: cart_tmp,
            total: total_tmp,
          })
          instance.close();
        });
        break;
    }
  },

  ChangeGoodsNum: function (e) {
    var that = this;
    var idList = wx.getStorageSync('ids');
    var numList = wx.getStorageSync('nums');
    var index = idList.indexOf(e.target.id.toString());
    if (index < 0) {
      Toast.fail('系统错误！');
    } else {
      var changeNum = e.detail - numList[index];
      numList[index] = e.detail; // 货品个数设置为 num 个
      var cart_tmp = that.data.cart;
      for (let i = 0; i < cart_tmp.length; ++i) {
        if (cart_tmp[i].g_id == e.target.id.toString()) {
          var price = cart_tmp[i].price / cart_tmp[i].num;
          cart_tmp[i].num = e.detail;
          cart_tmp[i].price += changeNum * price;
          that.setData({
            cart: cart_tmp,
            total: that.data.total + changeNum * price * 100
          })
        }
      }
    }
    wx.setStorageSync('ids', idList);
    wx.setStorageSync('nums', numList);
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function () {
    var that = this;
    // 获取收货地址
    var c_id = wx.getStorageSync('c_id');
    wx.request({
      url: app.enabledUrl + '/mall/getCustomerAddressByCid?c_id=' + c_id,
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        that.setData({
          addr: res.data[0]
        })
      }
    })
    // 获取购物车信息，计算总价
    var idList = wx.getStorageSync('ids');
    for (var i = 0; i < idList.length; ++i) {
      wx.request({
        url: app.enabledUrl + '/mall/getGoodsInfoByGid?g_id=' + idList[i],
        method: 'GET',
        header: {
          'content-type': 'application/json'
        },
        success: function (res) {
          var item = {};
          var idList = wx.getStorageSync('ids');
          var numList = wx.getStorageSync('nums');
          item.g_id = res.data.g_id.toString();
          item.num = numList[idList.indexOf(item.g_id)];
          item.name = res.data.name;
          item.description = res.data.description;
          if (wx.getStorageSync('c_type') == 'retail') {
            item.price = res.data.retail_price * item.num;
          } else {
            item.price = res.data.trade_price * item.num;
          }
          var cart_tmp = that.data.cart;
          cart_tmp.push(item);
          that.setData({
            total: that.data.total + item.price * 100,
            cart: cart_tmp
          })
        }
      })
    }
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
    let _this = this;
    _this.setData({
      total: 0,
      cart: []
    })
    _this.onLoad();
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