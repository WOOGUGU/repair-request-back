// 管理员数据表格渲染
layui.use(['table','form','layer'], function() {
    var table = layui.table;
    var $=layui.jquery;
    var form = layui.form;
    var layer=layui.layer;
    //展示所有的交易信息数据
    table.render({
        elem: '#adminData'
        , height: 600
        , url: '/v2/user/selectAllAdmin'
        , method: 'get'
        , headers: {
            "Cookie": document.cookie
        }
        , where: {}
        , cols: [
            [ //表头
                {field: 'id', title: '编号', width:250, sort: true, fixed: 'left'}
                ,{field: 'name', title: '姓名',width: 250}
                ,{field: 'username', title: '用户名',width: 250}
                ,{field: 'password', title: '密码', width: 250, templet:function(d){
                    return d.password=='*'?'**********':'**********';}}
                ,{field: 'roles', title: '权限', width: 250, templet:function(d){
                    return d.roles==''?'管理员':'管理员';}}
            ]
        ]
        , id: 'tableOne'
        , toolbar: '#toolbar'
        , page: true //开启分页
        , limits: [3, 5, 10]  //一页选择显示3,5或10条数据
        , limit: 10  //一页显示10条数据
        , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
            if (res.data.length == 1) {
                return {
                    "code": 0, //解析接口状态
                    "msg": "ok", //解析提示文本
                    "count": 1, //解析数据长度
                    "data": res.data //解析数据列表
                };
            }
            else {
                var result;
                if (this.page.curr) {
                    result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
                } else {
                    result = res.data.slice(0, this.limit);
                }
                return {
                    "code": 0, //解析接口状态
                    "msg": "ok", //解析提示文本
                    "count": res.data.length, //解析数据长度
                    "data": result //解析数据列表
                };
            }
        }
    });

    $('#do_search').on('click', function () {
        table.reload('tableOne', {
            method: 'get'
            , url: '/managerByExample'
            , where: {
                'name': $('#name').val()
            }
            , page: {
                curr: 1
            }
            , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
                var array = new Array(res.data);
                return {
                    "code": 0, //解析接口状态
                    "msg": "ok", //解析提示文本
                    "count": 1, //解析数据长度
                    "data": array //解析数据列表
                };
            }
        });
    });
});

// 维修员数据表格渲染
layui.use(['table','form','layer'], function() {
    var table = layui.table;
    var $=layui.jquery;
    var form = layui.form;
    var layer=layui.layer;
    //展示所有的交易信息数据
    table.render({
        elem: '#repairmanData'
        , height: 600
        , url: '/v2/user/selectAllRepairman'
        , method: 'get'
        , headers: {
            "Cookie": document.cookie
        }
        , where: {}
        , cols: [
            [ //表头
                {field: 'id', title: '编号', width:250, sort: true, fixed: 'left'}
                ,{field: 'name', title: '姓名',width: 250}
                ,{field: 'username', title: '用户名',width: 250}
                ,{field: 'password', title: '密码', width: 250, templet:function(d){
                    return d.password=='*'?'**********':'**********';}}
                ,{field: 'roles', title: '权限', width: 250, templet:function(d){
                    return d.roles==''?'维修员':'维修员';}}
            ]
        ]
        , id: 'tableOne'
        , toolbar: '#toolbar'
        , page: true //开启分页
        , limits: [3, 5, 10]  //一页选择显示3,5或10条数据
        , limit: 10  //一页显示10条数据
        , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
            if (res.data.length == 1) {
                return {
                    "code": 0, //解析接口状态
                    "msg": "ok", //解析提示文本
                    "count": 1, //解析数据长度
                    "data": res.data //解析数据列表
                };
            }
            else {
                var result;
                if (this.page.curr) {
                    result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
                } else {
                    result = res.data.slice(0, this.limit);
                }
                return {
                    "code": 0, //解析接口状态
                    "msg": "ok", //解析提示文本
                    "count": res.data.length, //解析数据长度
                    "data": result //解析数据列表
                };
            }
        }
    });

    $('#do_search').on('click', function () {
        table.reload('tableOne', {
            method: 'get'
            , url: '/managerByExample'
            , where: {
                'name': $('#name').val()
            }
            , page: {
                curr: 1
            }
            , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
                var array = new Array(res.data);
                return {
                    "code": 0, //解析接口状态
                    "msg": "ok", //解析提示文本
                    "count": 1, //解析数据长度
                    "data": array //解析数据列表
                };
            }
        });
    });
});

// 普通用户数据表格渲染
layui.use(['table','form','layer'], function() {
    var table = layui.table;
    var $=layui.jquery;
    var form = layui.form;
    var layer=layui.layer;
    //展示所有的交易信息数据
    table.render({
        elem: '#norUserData'
        , height: 600
        , url: '/v2/user/selectAllNorUser'
        , method: 'get'
        , headers: {
            "Cookie": document.cookie
        }
        , where: {}
        , cols: [
            [ //表头
                {field: 'id', title: '编号', width:250, sort: true, fixed: 'left'}
                ,{field: 'name', title: '姓名',width: 250}
                ,{field: 'username', title: '用户名',width: 250}
                ,{field: 'password', title: '密码', width: 250, templet:function(d){
                    return d.password=='*'?'**********':'**********';}}
                ,{field: 'roles', title: '权限', width: 250, templet:function(d){
                    return d.roles==''?'普通用户':'普通用户';}}
            ]
        ]
        , id: 'tableOne'
        , toolbar: '#toolbar'
        , page: true //开启分页
        , limits: [3, 5, 10]  //一页选择显示3,5或10条数据
        , limit: 10  //一页显示10条数据
        , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
            if (res.data.length == 1) {
                return {
                    "code": 0, //解析接口状态
                    "msg": "ok", //解析提示文本
                    "count": 1, //解析数据长度
                    "data": res.data //解析数据列表
                };
            }
            else {
                var result;
                if (this.page.curr) {
                    result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
                } else {
                    result = res.data.slice(0, this.limit);
                }
                return {
                    "code": 0, //解析接口状态
                    "msg": "ok", //解析提示文本
                    "count": res.data.length, //解析数据长度
                    "data": result //解析数据列表
                };
            }
        }
    });

    $('#do_search').on('click', function () {
        table.reload('tableOne', {
            method: 'get'
            , url: '/managerByExample'
            , where: {
                'name': $('#name').val()
            }
            , page: {
                curr: 1
            }
            , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
                var array = new Array(res.data);
                return {
                    "code": 0, //解析接口状态
                    "msg": "ok", //解析提示文本
                    "count": 1, //解析数据长度
                    "data": array //解析数据列表
                };
            }
        });
    });
});

function addMember() {
    var uname = $("#uname").val();
    var passwd = $("#passwd").val();
    var name = $("#name").val();
    if (uname === "" || passwd === "") {
        alert("用户名或密码不能为空");
        return;
    } else if (name === "") {
        alert("真实姓名不能为空");
        return;
    } else {
        $.ajax({
            url: '/v2/user/addUser',
            type: 'post',
            headers: {
                "Cookie": document.cookie
            },
            data: {
                "username": uname,
                "password": passwd,
                "name": name,
                "roleType": $('#roleType input[name="status"]:checked ').val()
            },
            success:function (res) {
                if (res.userMsg !== "") {
                    alert(res.userMsg);
                } else {
                    alert("发生未知错误，请重试");
                }
            },
            error:function () {
                alert("发生未知错误，请重试");
            }
        });
    }
}

function delMember() {
    $.ajax({
        url: '/manager',
        type: 'delete',
        headers: {
            'wrnm': localStorage.wrnm
        },
        data: {
            "name": $("#mname").val(),
        },
        success: function (res) {
            if (res.code == 200) {
                alert("删除成功");
            } else {
                if (res.message !== "") {
                    alert(res.message);
                }
                else {
                    alert("出现异常，请重试");
                }
            }
        },
        error: function (res) {
            if (res.responseJSON === "") {
                alert("出现异常，请重试");
            } else {
                alert(res.responseJSON.message);
            }
        }
    });
}