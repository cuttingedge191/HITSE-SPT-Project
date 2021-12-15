// pages/goods_detail/index.js
import Page from '../../libs/common/page';
import Toast from '../../libs/dist/toast/toast';

Page({
  data: {
    goodInfo:{ },
    goods:[
      {
        id:'1',
        imageURL:'https://img.yzcdn.cn/vant/ipad.jpeg',
        tag:'特价',
        price:'2799.99',
        title:'苹果（Apple) iPad 10.2英寸平板电脑【2021年款iPad】（64GB WLAN版/MK2K3CH/A） 深空灰色',
        desc:'超30万人店内购买 | 新款平板',
      },
      { 
        id:'2',
        imageURL:'https://img.yzcdn.cn/vant/cat.jpeg',
        tag:'打折',
        price:'9.99',
        title:'猫咪V3.0限定版',
        desc:'超30万人店内购买，你值得拥有！'
        },
        {
          id:'3',
          imageURL:'https://img.yzcdn.cn/vant/ipad.jpeg',
          tag:'特价',
          price:'2799.99',
          title:'苹果（Apple) iPad 10.2英寸平板电脑【2021年款iPad】（64GB WLAN版/MK2K3CH/A） 深空灰色',
          desc:'超30万人店内购买 | 新款平板'
          },
      ]
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
    var that = this;
    wx.request({
      url: 'http://localhost:8080/mall/getGoodsInfoByGid?g_id='+options.g_id,
      method: 'GET',
      header: {
        'content-type':'application/json'
      },
      success: function(res){
        that.setData({goodInfo : res.data})
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