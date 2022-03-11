// // 管理员数据表格渲染
// layui.use(['table','form','layer'], function() {
//     var table = layui.table;
//     var $=layui.jquery;
//     var form = layui.form;
//     var layer=layui.layer;
//     //展示所有的交易信息数据
//     table.render({
//         elem: '#adminData'
//         , height: 600
//         , url: '/v2/user/selectAllAdmin'
//         , method: 'get'
//         , headers: {
//             "Cookie": document.cookie
//         }
//         , where: {}
//         , cols: [
//             [ //表头
//                 {field: 'id', title: '编号', width:250, sort: true, fixed: 'left'}
//                 ,{field: 'name', title: '姓名',width: 250}
//                 ,{field: 'username', title: '用户名',width: 250}
//                 ,{field: 'password', title: '密码', width: 250, templet:function(d){
//                     return d.password=='*'?'**********':'**********';}}
//                 ,{field: 'roles', title: '权限', width: 250, templet:function(d){
//                     return d.roles==''?'管理员':'管理员';}}
//             ]
//         ]
//         , id: 'tableOne'
//         , toolbar: '#toolbar'
//         , page: true //开启分页
//         , limits: [3, 5, 10]  //一页选择显示3,5或10条数据
//         , limit: 10  //一页显示10条数据
//         , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
//             if (res.data.length == 1) {
//                 return {
//                     "code": 0, //解析接口状态
//                     "msg": "ok", //解析提示文本
//                     "count": 1, //解析数据长度
//                     "data": res.data //解析数据列表
//                 };
//             }
//             else {
//                 var result;
//                 if (this.page.curr) {
//                     result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
//                 } else {
//                     result = res.data.slice(0, this.limit);
//                 }
//                 return {
//                     "code": 0, //解析接口状态
//                     "msg": "ok", //解析提示文本
//                     "count": res.data.length, //解析数据长度
//                     "data": result //解析数据列表
//                 };
//             }
//         }
//     });
//
//     $('#searchAdmin').on('click', function () {
//         table.reload('tableOne', {
//             method: 'get'
//             , url: '/v2/user/selectUser'
//             , headers: {
//                 "Cookie": document.cookie
//             }
//             , where: {
//                 'userId': $('#userId').val(),
//                 'username': $('#username').val(),
//                 'name': $('#name').val()
//             }
//             , page: {
//                 curr: 1
//             }
//             , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
//                 if (res.data.length == 1) {
//                     return {
//                         "code": 0, //解析接口状态
//                         "msg": "ok", //解析提示文本
//                         "count": 1, //解析数据长度
//                         "data": res.data //解析数据列表
//                     };
//                 }
//                 else {
//                     var result;
//                     if (this.page.curr) {
//                         result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
//                     } else {
//                         result = res.data.slice(0, this.limit);
//                     }
//                     return {
//                         "code": 0, //解析接口状态
//                         "msg": "ok", //解析提示文本
//                         "count": res.data.length, //解析数据长度
//                         "data": result //解析数据列表
//                     };
//                 }
//             }
//         });
//     });
// });
//
// // 维修员数据表格渲染
// layui.use(['table','form','layer'], function() {
//     var table = layui.table;
//     var $=layui.jquery;
//     var form = layui.form;
//     var layer=layui.layer;
//     //展示所有的交易信息数据
//     table.render({
//         elem: '#repairmanData'
//         , height: 600
//         , url: '/v2/user/selectAllRepairman'
//         , method: 'get'
//         , headers: {
//             "Cookie": document.cookie
//         }
//         , where: {}
//         , cols: [
//             [ //表头
//                 {field: 'id', title: '编号', width:250, sort: true, fixed: 'left'}
//                 ,{field: 'name', title: '姓名',width: 250}
//                 ,{field: 'username', title: '用户名',width: 250}
//                 ,{field: 'password', title: '密码', width: 250, templet:function(d){
//                     return d.password=='*'?'**********':'**********';}}
//                 ,{field: 'roles', title: '权限', width: 250, templet:function(d){
//                     return d.roles==''?'维修员':'维修员';}}
//             ]
//         ]
//         , id: 'tableTwo'
//         , toolbar: '#toolbar'
//         , page: true //开启分页
//         , limits: [3, 5, 10]  //一页选择显示3,5或10条数据
//         , limit: 10  //一页显示10条数据
//         , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
//             if (res.data.length == 1) {
//                 return {
//                     "code": 0, //解析接口状态
//                     "msg": "ok", //解析提示文本
//                     "count": 1, //解析数据长度
//                     "data": res.data //解析数据列表
//                 };
//             }
//             else {
//                 var result;
//                 if (this.page.curr) {
//                     result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
//                 } else {
//                     result = res.data.slice(0, this.limit);
//                 }
//                 return {
//                     "code": 0, //解析接口状态
//                     "msg": "ok", //解析提示文本
//                     "count": res.data.length, //解析数据长度
//                     "data": result //解析数据列表
//                 };
//             }
//         }
//     });
//
//     $('#searchRepairman').on('click', function () {
//         table.reload('tableTwo', {
//             method: 'get'
//             , url: '/v2/user/selectUser'
//             , headers: {
//                 "Cookie": document.cookie
//             }
//             , where: {
//                 'userId': $('#userId').val(),
//                 'username': $('#username').val(),
//                 'name': $('#name').val()
//             }
//             , page: {
//                 curr: 1
//             }
//             , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
//                 if (res.data.length == 1) {
//                     return {
//                         "code": 0, //解析接口状态
//                         "msg": "ok", //解析提示文本
//                         "count": 1, //解析数据长度
//                         "data": res.data //解析数据列表
//                     };
//                 }
//                 else {
//                     var result;
//                     if (this.page.curr) {
//                         result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
//                     } else {
//                         result = res.data.slice(0, this.limit);
//                     }
//                     return {
//                         "code": 0, //解析接口状态
//                         "msg": "ok", //解析提示文本
//                         "count": res.data.length, //解析数据长度
//                         "data": result //解析数据列表
//                     };
//                 }
//             }
//         });
//     });
// });
//
// // 普通用户数据表格渲染
// layui.use(['table','form','layer'], function() {
//     var table = layui.table;
//     var $=layui.jquery;
//     var form = layui.form;
//     var layer=layui.layer;
//     //展示所有的交易信息数据
//     table.render({
//         elem: '#norUserData'
//         , height: 600
//         , url: '/v2/user/selectAllNorUser'
//         , method: 'get'
//         , headers: {
//             "Cookie": document.cookie
//         }
//         , where: {}
//         , cols: [
//             [ //表头
//                 {field: 'id', title: '编号', width:250, sort: true, fixed: 'left'}
//                 ,{field: 'name', title: '姓名',width: 250}
//                 ,{field: 'username', title: '用户名',width: 250}
//                 ,{field: 'password', title: '密码', width: 250, templet:function(d){
//                     return d.password=='*'?'**********':'**********';}}
//                 ,{field: 'roles', title: '权限', width: 250, templet:function(d){
//                     return d.roles==''?'普通用户':'普通用户';}}
//             ]
//         ]
//         , id: 'tableThree'
//         , toolbar: '#toolbar'
//         , page: true //开启分页
//         , limits: [3, 5, 10]  //一页选择显示3,5或10条数据
//         , limit: 10  //一页显示10条数据
//         , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
//             if (res.data.length == 1) {
//                 return {
//                     "code": 0, //解析接口状态
//                     "msg": "ok", //解析提示文本
//                     "count": 1, //解析数据长度
//                     "data": res.data //解析数据列表
//                 };
//             }
//             else {
//                 var result;
//                 if (this.page.curr) {
//                     result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
//                 } else {
//                     result = res.data.slice(0, this.limit);
//                 }
//                 return {
//                     "code": 0, //解析接口状态
//                     "msg": "ok", //解析提示文本
//                     "count": res.data.length, //解析数据长度
//                     "data": result //解析数据列表
//                 };
//             }
//         }
//     });
//
//     $('#searchNorUser').on('click', function () {
//         table.reload('tableThree', {
//             method: 'get'
//             , url: '/v2/user/selectUser'
//             , headers: {
//                 "Cookie": document.cookie
//             }
//             , where: {
//                 'userId': $('#userId').val(),
//                 'username': $('#username').val(),
//                 'name': $('#name').val()
//             }
//             , page: {
//                 curr: 1
//             }
//             , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
//                 if (res.data.length == 1) {
//                     return {
//                         "code": 0, //解析接口状态
//                         "msg": "ok", //解析提示文本
//                         "count": 1, //解析数据长度
//                         "data": res.data //解析数据列表
//                     };
//                 } else {
//                     var result;
//                     if (this.page.curr) {
//                         result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
//                     } else {
//                         result = res.data.slice(0, this.limit);
//                     }
//                     return {
//                         "code": 0, //解析接口状态
//                         "msg": "ok", //解析提示文本
//                         "count": res.data.length, //解析数据长度
//                         "data": result //解析数据列表
//                     };
//                 }
//             }
//         });
//     });
// });
//
// // 增加用户
// function addMember() {
//     var uname = $("#uname").val();
//     var passwd = $("#passwd").val();
//     var name = $("#name").val();
//     if (uname === "" || passwd === "") {
//         alert("用户名或密码不能为空");
//         return;
//     } else if (name === "") {
//         alert("真实姓名不能为空");
//         return;
//     } else {
//         $.ajax({
//             url: '/v2/user/addUser',
//             type: 'post',
//             headers: {
//                 "Cookie": document.cookie
//             },
//             data: {
//                 "username": uname,
//                 "password": passwd,
//                 "name": name,
//                 "roleType": $('#roleType input[name="status"]:checked ').val()
//             },
//             success: function (res) {
//                 if (res.userMsg !== "") {
//                     alert(res.userMsg);
//                 } else {
//                     alert("发生未知错误，请重试");
//                 }
//             },
//             error: function () {
//                 alert("发生未知错误，请重试");
//             }
//         });
//     }
// }
//
// // 修改用户
// function updateMember() {
//     var userId = $("#userId").val();
//     var uname = $("#uname").val();
//     var oldPassword = $("#oldPassword").val();
//     var newPassword = $("#newPassword").val();
//     if (oldPassword !== "") {
//         oldPassword = hexMD5(oldPassword).toUpperCase();
//     }
//     if (newPassword !== "") {
//         newPassword = hexMD5(newPassword).toUpperCase();
//     }
//     var name = $("#name").val();
//     if (userId === "") {
//         alert("用户Id不能为空");
//     } else {
//         $.ajax({
//             url: '/v2/user/updateUser',
//             type: 'post',
//             headers: {
//                 "Cookie": document.cookie
//             },
//             data: {
//                 "userId": userId,
//                 "username": uname,
//                 "oldPassword": oldPassword,
//                 "newPassword": newPassword,
//                 "name": name,
//                 "roleType": $('#roleType input[name="status"]:checked ').val()
//             },
//             success: function (res) {
//                 if (res.userMsg !== "") {
//                     alert(res.userMsg);
//                 } else {
//                     alert("发生未知错误，请重试");
//                 }
//             },
//             error: function () {
//                 alert("发生未知错误，请重试");
//             }
//         });
//     }
// }
//
// // 删除用户
// function delMember() {
//     if ($("#userId").val() === "" && $("#username").val() === "") {
//         alert("用户Id和用户名不能同时为空");
//         return;
//     }
//     $.ajax({
//         url: '/v2/user/deleteUser',
//         type: 'post',
//         headers: {
//             "Cookie": document.cookie
//         },
//         data: {
//             "userId": $("#userId").val(),
//             "username": $("#username").val()
//         },
//         success: function (res) {
//             if (res.userMsg !== "") {
//                 alert(res.userMsg);
//             } else {
//                 alert("发生未知错误，请重试");
//             }
//         },
//         error: function () {
//             alert("发生未知错误，请重试");
//         }
//     });
// }
//
// // 工单数据表格渲染
// layui.use(['table','form','layer'], function() {
//     var table = layui.table;
//     var $=layui.jquery;
//     var form = layui.form;
//     var layer=layui.layer;
//     //展示所有的交易信息数据
//     table.render({
//         elem: '#orderData'
//         , height: 600
//         , url: '/v2/order/selectAllOrder'
//         , method: 'get'
//         , headers: {
//             "Cookie": document.cookie
//         }
//         , where: {}
//         , cols: [
//             [ //表头
//                 {field: 'id', title: '编号', width:250, sort: true, fixed: 'left'}
//                 ,{field: 'sender', title: '发起者姓名',width: 250}
//                 ,{field: 'username', title: '用户名',width: 250}
//                 ,{field: 'tel', title: '联系方式', width: 250}
//                 ,{field: 'type', title: '工单类型', width: 250}
//                 ,{field: 'des', title: '故障描述', width: 250}
//                 ,{field: 'position', title: '故障位置', width: 250}
//                 ,{field: 'timeSubscribe', title: '预约上门时间', width: 250}
//                 ,{field: 'progress', title: '状态', width: 250, templet:function(d){
//                     if (d.progress === 4) {
//                         return '审核不通过';
//                     } else if (d.progress === 3) {
//                         return '用户取消';
//                     } else if (d.progress === 0) {
//                         return '待审核';
//                     } else if (d.progress === 1) {
//                         return '待处理';
//                     } else if (d.progress === 2) {
//                         return '已处理';
//                     } else {
//                         return '出现异常';
//                     }
//                 }
//             }
//                 , {field: 'solver', title: '技术人员', width: 250}
//                 , {field: 'timeStart', title: '发起时间', width: 250}
//                 , {field: 'timeDistribution', title: '分配时间', width: 250}
//                 , {field: 'timeEnd', title: '解决时间', width: 250}
//                 , {field: 'feedback', title: '用户反馈', width: 250}
//                 , {title: '操作', align: 'center', toolbar: '#order'}
//             ]
//         ]
//         , id: 'tableFour'
//         , toolbar: '#toolbar'
//         , page: true //开启分页
//         , limits: [3, 5, 10]  //一页选择显示3,5或10条数据
//         , limit: 10  //一页显示10条数据
//         , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
//             if (res.data.length == 1) {
//                 return {
//                     "code": 0, //解析接口状态
//                     "msg": "ok", //解析提示文本
//                     "count": 1, //解析数据长度
//                     "data": res.data //解析数据列表
//                 };
//             }
//             else {
//                 var result;
//                 if (this.page.curr) {
//                     result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
//                 } else {
//                     result = res.data.slice(0, this.limit);
//                 }
//                 return {
//                     "code": 0, //解析接口状态
//                     "msg": "ok", //解析提示文本
//                     "count": res.data.length, //解析数据长度
//                     "data": result //解析数据列表
//                 };
//             }
//         }
//     });
//
//     $('#searchOrder').on('click', function () {
//         table.reload('tableFour', {
//             method: 'get'
//             , url: '/v2/order/selectOrder'
//             , headers: {
//                 "Cookie": document.cookie
//             }
//             , where: {
//                 'orderId': $('#orderId').val(),
//                 'username': $('#username').val(),
//                 'sender': $('#sender').val(),
//                 'tel': $('#tel').val(),
//                 'type': $('#type').val(),
//                 'des': $('#des').val(),
//                 'position': $('#position').val(),
//                 'timeSubscribe': $('#timeSubscribe').val(),
//                 'progress': $('#progress').val(),
//                 'solver': $('#solver').val(),
//                 'timeStart': $('#timeStart').val(),
//                 'timeDistribution': $('#timeDistribution').val(),
//                 'timeEnd': $('#timeEnd').val(),
//                 'feedback': $('#feedback').val()
//             }
//             , page: {
//                 curr: 1
//             }
//             , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
//                 if (res.data.length == 1) {
//                     return {
//                         "code": 0, //解析接口状态
//                         "msg": "ok", //解析提示文本
//                         "count": 1, //解析数据长度
//                         "data": res.data //解析数据列表
//                     };
//                 }
//                 else {
//                     var result;
//                     if (this.page.curr) {
//                         result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
//                     } else {
//                         result = res.data.slice(0, this.limit);
//                     }
//                     return {
//                         "code": 0, //解析接口状态
//                         "msg": "ok", //解析提示文本
//                         "count": res.data.length, //解析数据长度
//                         "data": result //解析数据列表
//                     };
//                 }
//             }
//         });
//     });
// });
//
// // 增加工单
// function addOrder() {
//     var uname = $("#uname").val();
//     var passwd = $("#passwd").val();
//     var name = $("#name").val();
//     if (uname === "" || passwd === "") {
//         alert("用户名或密码不能为空");
//         return;
//     } else if (name === "") {
//         alert("真实姓名不能为空");
//         return;
//     } else {
//         $.ajax({
//             url: '/v2/user/addUser',
//             type: 'post',
//             headers: {
//                 "Cookie": document.cookie
//             },
//             data: {
//                 "username": uname,
//                 "password": passwd,
//                 "name": name,
//                 "roleType": $('#roleType input[name="status"]:checked ').val()
//             },
//             success:function (res) {
//                 if (res.userMsg !== "") {
//                     alert(res.userMsg);
//                 } else {
//                     alert("发生未知错误，请重试");
//                 }
//             },
//             error: function () {
//                 alert("发生未知错误，请重试");
//             }
//         });
//     }
// }
//
// // 修改工单
// function updateOrder() {
//     var userId = $("#userId").val();
//     var uname = $("#uname").val();
//     var passwd = $("#passwd").val();
//     var name = $("#name").val();
//     if (uname === "" || passwd === "") {
//         alert("用户名或密码不能为空");
//         return;
//     } else if (name === "") {
//         alert("真实姓名不能为空");
//         return;
//     } else {
//         $.ajax({
//             url: '/v2/order/updateOrder',
//             type: 'post',
//             headers: {
//                 "Cookie": document.cookie
//             },
//             data: {
//                 "userId": userId,
//                 "username": uname,
//                 "password": passwd,
//                 "name": name,
//                 "roleType": $('#roleType input[name="status"]:checked ').val()
//             },
//             success: function (res) {
//                 if (res.userMsg !== "") {
//                     alert(res.userMsg);
//                 } else {
//                     alert("发生未知错误，请重试");
//                 }
//             },
//             error: function () {
//                 alert("发生未知错误，请重试");
//             }
//         });
//     }
// }
//
// // 审核工单跳转
// function toCheckOrder() {
//     layui.use(['table'], function () {
//         var table = layui.table;
//         table.on('tool(order)', function (obj) {
//             var tr = obj.data;
//             window.localStorage.setItem("orderId", tr.id);
//             window.localStorage.setItem("progress", tr.progress);
//             window.location.href = "http://localhost:8090/updateOrder.html";
//         })
//     });
// }
//
// // 审核工单
// function checkOrder() {
//     $.ajax({
//         url: '/v2/order/checkOrder',
//         type: 'post',
//         headers: {
//             "Cookie": document.cookie
//         },
//         data: {
//             "orderId": window.localStorage.getItem("orderId"),
//             "progress": $('#progress').val()
//         },
//         success: function (res) {
//             if (res.userMsg !== "") {
//                 alert(res.userMsg);
//             } else {
//                 alert("发生未知错误，请重试");
//             }
//             window.location.href = "http://localhost:8090/orderList.html";
//         },
//         error: function () {
//             alert("发生未知错误，请重试");
//         }
//     });
// }
//
// // 删除工单
// function delOrder() {
//     if ($("#orderId").val() === "") {
//         alert("orderId不能为空");
//         return;
//     }
//     $.ajax({
//         url: '/v2/order/deleteOrder',
//         type: 'post',
//         headers: {
//             "Cookie": document.cookie
//         },
//         data: {
//             "orderId": $("#orderId").val()
//         },
//         success: function (res) {
//             if (res.userMsg !== "") {
//                 alert(res.userMsg);
//             } else {
//                 alert("发生未知错误，请重试");
//             }
//         },
//         error: function () {
//             alert("发生未知错误，请重试");
//         }
//     });
// }
//
// // 文章数据表格渲染
// layui.use(['table','form','layer'], function() {
//     var table = layui.table;
//     var $=layui.jquery;
//     var form = layui.form;
//     var layer=layui.layer;
//     //展示所有的交易信息数据
//     table.render({
//         elem: '#articleData'
//         , height: 600
//         , url: '/v2/article/selectAllArticle'
//         , method: 'get'
//         , headers: {
//             "Cookie": document.cookie
//         }
//         , where: {}
//         , cols: [
//             [ //表头
//                 {field: 'id', title: '编号', width:250, sort: true, fixed: 'left'}
//                 ,{field: 'createTime', title: '创建时间',width: 250}
//                 ,{field: 'updateTime', title: '修改时间',width: 250}
//                 ,{field: 'contentPath', title: '内容路径', width: 250}
//                 ,{field: 'author', title: '作者', width: 250}
//                 ,{field: 'displayStatus', title: '状态', width: 250, templet:function(d){
//                     return d.displayStatus===1?'展示':'隐藏';}}
//             ]
//         ]
//         , id: 'tableFive'
//         , toolbar: '#toolbar'
//         , page: true //开启分页
//         , limits: [3, 5, 10]  //一页选择显示3,5或10条数据
//         , limit: 10  //一页显示10条数据
//         , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
//             if (res.data.length == 1) {
//                 return {
//                     "code": 0, //解析接口状态
//                     "msg": "ok", //解析提示文本
//                     "count": 1, //解析数据长度
//                     "data": res.data //解析数据列表
//                 };
//             }
//             else {
//                 var result;
//                 if (this.page.curr) {
//                     result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
//                 } else {
//                     result = res.data.slice(0, this.limit);
//                 }
//                 return {
//                     "code": 0, //解析接口状态
//                     "msg": "ok", //解析提示文本
//                     "count": res.data.length, //解析数据长度
//                     "data": result //解析数据列表
//                 };
//             }
//         }
//     });
//
//     $('#searchArticle').on('click', function () {
//         table.reload('tableFive', {
//             method: 'get'
//             , url: '/v2/article/selectArticle'
//             , headers: {
//                 "Cookie": document.cookie
//             }
//             , where: {
//                 'articleId': $('#articleId').val(),
//                 'author': $('#author').val(),
//                 'displayStatus': $('#displayStatus').val()
//             }
//             , page: {
//                 curr: 1
//             }
//             , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
//                 if (res.data.length == 1) {
//                     return {
//                         "code": 0, //解析接口状态
//                         "msg": "ok", //解析提示文本
//                         "count": 1, //解析数据长度
//                         "data": res.data //解析数据列表
//                     };
//                 }
//                 else {
//                     var result;
//                     if (this.page.curr) {
//                         result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
//                     } else {
//                         result = res.data.slice(0, this.limit);
//                     }
//                     return {
//                         "code": 0, //解析接口状态
//                         "msg": "ok", //解析提示文本
//                         "count": res.data.length, //解析数据长度
//                         "data": result //解析数据列表
//                     };
//                 }
//             }
//         });
//     });
// });
//
// // 增加文章
// function addArticle() {
//     var contentPath = $("#contentPath").val();
//     var author = $("#author").val();
//     if (contentPath === "" || contentPath === "") {
//         alert("文章网址不能为空");
//         return;
//     } else if (author === "") {
//         alert("文章作者不能为空");
//         return;
//     } else {
//         $.ajax({
//             url: '/v2/article/addArticle',
//             type: 'post',
//             headers: {
//                 "Cookie": document.cookie
//             },
//             data: {
//                 "contentPath": contentPath,
//                 "author": author,
//                 "displayStatus": $('#displayStatus input[name="status"]:checked ').val()
//             },
//             success: function (res) {
//                 if (res.userMsg !== "") {
//                     alert(res.userMsg);
//                 } else {
//                     alert("发生未知错误，请重试");
//                 }
//             },
//             error: function () {
//                 alert("发生未知错误，请重试");
//             }
//         });
//     }
// }
//
// // 删除文章
// function delArticle() {
//     if ($("#articleId").val() === "") {
//         alert("文章Id不能为空");
//         return;
//     }
//     $.ajax({
//         url: '/v2/article/deleteArticle',
//         type: 'post',
//         headers: {
//             "Cookie": document.cookie
//         },
//         data: {
//             "articleId": $("#articleId").val()
//         },
//         success: function (res) {
//             if (res.userMsg !== "") {
//                 alert(res.userMsg);
//             } else {
//                 alert("发生未知错误，请重试");
//             }
//         },
//         error: function () {
//             alert("发生未知错误，请重试");
//         }
//     });
// }
//
// // 修改文章
// function updateArticle() {
//     var articleId = $("#articleId").val();
//     var contentPath = $("#contentPath").val();
//     var author = $("#author").val();
//     if (articleId === "") {
//         alert("文章Id不能为空");
//         return;
//     } else if (contentPath === "") {
//         alert("文章网址不能为空");
//         return;
//     } else if (author === "") {
//         alert("文章作者不能为空");
//         return;
//     } else {
//         $.ajax({
//             url: '/v2/article/updateArticle',
//             type: 'post',
//             headers: {
//                 "Cookie": document.cookie
//             },
//             data: {
//                 "articleId": articleId,
//                 "contentPath": contentPath,
//                 "author": author,
//                 "displayStatus": $('#displayStatus input[name="status"]:checked ').val()
//             },
//             success: function (res) {
//                 if (res.userMsg !== "") {
//                     alert(res.userMsg);
//                 } else {
//                     alert("发生未知错误，请重试");
//                 }
//             },
//             error: function () {
//                 alert("发生未知错误，请重试");
//             }
//         });
//     }
// }
//
// // 轮播图数据表格渲染
// layui.use(['table', 'form', 'layer'], function () {
//     var table = layui.table;
//     var $ = layui.jquery;
//     var form = layui.form;
//     var layer = layui.layer;
//     //展示所有的交易信息数据
//     table.render({
//         elem: '#slideData'
//         , height: 600
//         , url: '/v2/slide/selectAllSlide'
//         , method: 'get'
//         , headers: {
//             "Cookie": document.cookie
//         }
//         , where: {}
//         , cols: [
//             [ //表头
//                 {field: 'id', title: '编号', width: 250, sort: true, fixed: 'left'}
//                 , {field: 'imgPath', title: '图片路径', width: 250}
//                 , {field: 'author', title: '上传者', width: 250}
//                 , {field: 'submitTime', title: '上传时间', width: 250}
//             ]
//         ]
//         , id: 'tableSix'
//         , toolbar: '#toolbar'
//         , page: true //开启分页
//         , limits: [3, 5, 10]  //一页选择显示3,5或10条数据
//         , limit: 10  //一页显示10条数据
//         , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
//             if (res.data.length == 1) {
//                 return {
//                     "code": 0, //解析接口状态
//                     "msg": "ok", //解析提示文本
//                     "count": 1, //解析数据长度
//                     "data": res.data //解析数据列表
//                 };
//             } else {
//                 var result;
//                 if (this.page.curr) {
//                     result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
//                 } else {
//                     result = res.data.slice(0, this.limit);
//                 }
//                 return {
//                     "code": 0, //解析接口状态
//                     "msg": "ok", //解析提示文本
//                     "count": res.data.length, //解析数据长度
//                     "data": result //解析数据列表
//                 };
//             }
//         }
//     });
//
//     $('#searchSlide').on('click', function () {
//         table.reload('tableSix', {
//             method: 'get'
//             , url: '/v2/slide/selectSlide'
//             , headers: {
//                 "Cookie": document.cookie
//             }
//             , where: {
//                 'slideId': $('#slideId').val(),
//                 'author': $('#author').val(),
//             }
//             , page: {
//                 curr: 1
//             }
//             , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
//                 if (res.data.length == 1) {
//                     return {
//                         "code": 0, //解析接口状态
//                         "msg": "ok", //解析提示文本
//                         "count": 1, //解析数据长度
//                         "data": res.data //解析数据列表
//                     };
//                 } else {
//                     var result;
//                     if (this.page.curr) {
//                         result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
//                     } else {
//                         result = res.data.slice(0, this.limit);
//                     }
//                     return {
//                         "code": 0, //解析接口状态
//                         "msg": "ok", //解析提示文本
//                         "count": res.data.length, //解析数据长度
//                         "data": result //解析数据列表
//                     };
//                 }
//             }
//         });
//     });
// });
//
// // 删除轮播图
// function delSlide() {
//     if ($("#slideId").val() === "") {
//         alert("轮播图Id不能为空");
//         return;
//     }
//     $.ajax({
//         url: '/v2/slide/deleteSlide',
//         type: 'post',
//         headers: {
//             "Cookie": document.cookie
//         },
//         data: {
//             "slideId": $("#slideId").val()
//         },
//         success: function (res) {
//             if (res.userMsg !== "") {
//                 alert(res.userMsg);
//             } else {
//                 alert("发生未知错误，请重试");
//             }
//         },
//         error: function () {
//             alert("发生未知错误，请重试");
//         }
//     });
// }
//
// // 通知数据表格渲染
// layui.use(['table', 'form', 'layer'], function () {
//     var table = layui.table;
//     var $ = layui.jquery;
//     var form = layui.form;
//     var layer = layui.layer;
//     //展示所有的交易信息数据
//     table.render({
//         elem: '#noticeData'
//         , height: 600
//         , url: '/v2/notice/selectAllNotice'
//         , method: 'get'
//         , headers: {
//             "Cookie": document.cookie
//         }
//         , where: {}
//         , cols: [
//             [ //表头
//                 {field: 'id', title: '编号', width: 250, sort: true, fixed: 'left'}
//                 , {field: 'content', title: '公告网址', width: 250}
//                 , {field: 'author', title: '发布者', width: 250}
//                 , {field: 'createTime', title: '创建时间', width: 250}
//                 , {field: 'announceTime', title: '发布时间', width: 250}
//                 , {field: 'updateTime', title: '修改时间', width: 250}
//                 , {
//                 field: 'displayStatus', title: '状态', width: 250, templet: function (d) {
//                     return d.displayStatus === 1 ? '展示' : '隐藏';
//                 }
//             }
//             ]
//         ]
//         , id: 'tableSeven'
//         , toolbar: '#toolbar'
//         , page: true //开启分页
//         , limits: [3, 5, 10]  //一页选择显示3,5或10条数据
//         , limit: 10  //一页显示10条数据
//         , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
//             if (res.data.length == 1) {
//                 return {
//                     "code": 0, //解析接口状态
//                     "msg": "ok", //解析提示文本
//                     "count": 1, //解析数据长度
//                     "data": res.data //解析数据列表
//                 };
//             } else {
//                 var result;
//                 if (this.page.curr) {
//                     result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
//                 } else {
//                     result = res.data.slice(0, this.limit);
//                 }
//                 return {
//                     "code": 0, //解析接口状态
//                     "msg": "ok", //解析提示文本
//                     "count": res.data.length, //解析数据长度
//                     "data": result //解析数据列表
//                 };
//             }
//         }
//     });
//
//     $('#searchNotice').on('click', function () {
//         table.reload('tableSeven', {
//             method: 'get'
//             , url: '/v2/notice/selectNotice'
//             , headers: {
//                 "Cookie": document.cookie
//             }
//             , where: {
//                 'noticeId': $('#noticeId').val(),
//                 'author': $('#author').val(),
//                 'displayStatus': $('#displayStatus').val()
//             }
//             , page: {
//                 curr: 1
//             }
//             , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
//                 if (res.data.length == 1) {
//                     return {
//                         "code": 0, //解析接口状态
//                         "msg": "ok", //解析提示文本
//                         "count": 1, //解析数据长度
//                         "data": res.data //解析数据列表
//                     };
//                 } else {
//                     var result;
//                     if (this.page.curr) {
//                         result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
//                     } else {
//                         result = res.data.slice(0, this.limit);
//                     }
//                     return {
//                         "code": 0, //解析接口状态
//                         "msg": "ok", //解析提示文本
//                         "count": res.data.length, //解析数据长度
//                         "data": result //解析数据列表
//                     };
//                 }
//             }
//         });
//     });
// });
//
// // 增加通知
// function addNotice() {
//     var content = $("#content").val();
//     var author = $("#author").val();
//     if (content === "" || content === "") {
//         alert("通知网址不能为空");
//         return;
//     } else if (author === "") {
//         alert("发布者不能为空");
//         return;
//     } else {
//         $.ajax({
//             url: '/v2/notice/addNotice',
//             type: 'post',
//             headers: {
//                 "Cookie": document.cookie
//             },
//             data: {
//                 "content": content,
//                 "author": author,
//                 "displayStatus": $('#displayStatus input[name="status"]:checked ').val()
//             },
//             success: function (res) {
//                 if (res.userMsg !== "") {
//                     alert(res.userMsg);
//                 } else {
//                     alert("发生未知错误，请重试");
//                 }
//             },
//             error: function () {
//                 alert("发生未知错误，请重试");
//             }
//         });
//     }
// }
//
// // 删除通知
// function delNotice() {
//     if ($("#noticeId").val() === "") {
//         alert("通知Id不能为空");
//         return;
//     }
//     $.ajax({
//         url: '/v2/notice/deleteNotice',
//         type: 'post',
//         headers: {
//             "Cookie": document.cookie
//         },
//         data: {
//             "noticeId": $("#noticeId").val()
//         },
//         success: function (res) {
//             if (res.userMsg !== "") {
//                 alert(res.userMsg);
//             } else {
//                 alert("发生未知错误，请重试");
//             }
//         },
//         error: function () {
//             alert("发生未知错误，请重试");
//         }
//     });
// }
//
// // 修改通知
// function updateNotice() {
//     var noticeId = $("#noticeId").val();
//     var content = $("#content").val();
//     var author = $("#author").val();
//     if (noticeId === "") {
//         alert("通知Id不能为空");
//         return;
//     } else if (content === "") {
//         alert("通知网址不能为空");
//         return;
//     } else if (author === "") {
//         alert("发布者不能为空");
//         return;
//     } else {
//         $.ajax({
//             url: '/v2/notice/updateNotice',
//             type: 'post',
//             headers: {
//                 "Cookie": document.cookie
//             },
//             data: {
//                 "noticeId": noticeId,
//                 "content": content,
//                 "author": author,
//                 "displayStatus": $('#displayStatus input[name="status"]:checked ').val()
//             },
//             success: function (res) {
//                 if (res.userMsg !== "") {
//                     alert(res.userMsg);
//                 } else {
//                     alert("发生未知错误，请重试");
//                 }
//             },
//             error: function () {
//                 alert("发生未知错误，请重试");
//             }
//         });
//     }
// }