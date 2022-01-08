/**
 * 在此处设置后端服务器
 * localTest: 是否启用本地
 * localUrl: 本地测试服务器地址
 * remoteUrl: 远程服务器地址
 */
var serverConf = {
  localTest: true,
  localUrl: 'http://localhost:8080',
  remoteUrl: 'http://192.168.1.9:8080'
}

var enabledUrl = '';

if (serverConf.localTest) {
  enabledUrl = serverConf.localUrl;
} else {
  enabledUrl = serverConf.remoteUrl;
}

module.exports = {
  enabledUrl: enabledUrl
};