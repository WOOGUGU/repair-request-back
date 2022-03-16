// 普通用户数据表格渲染
layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table;
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    //展示所有的交易信息数据
    table.render({
        elem: '#userData'
        , height: 600
        , url: '/v2/user/selectAllUser'
        , method: 'get'
        , headers: {
            "Cookie": document.cookie
        }
        , where: {}
        , cols: [
            [ //表头
                {field: 'id', title: '编号', width: 50, sort: true, fixed: 'left'}
                , {field: 'name', title: '姓名', width: 250}
                , {field: 'username', title: '用户名', width: 250}
                , {
                field: 'password', title: '密码', width: 250, templet: function (d) {
                    return d.password == '*' ? '**********' : '**********';
                }
            }
                , {
                field: 'roles', title: '权限', width: 250, templet: function (d) {
                    return d.roles == '' ? '普通用户' : '普通用户';
                }
            }
                , {title: '操作', align: 'center', width: 180, toolbar: '#user'}
            ]
        ]
        , id: 'tableThree'
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
            } else {
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

    $('#searchUser').on('click', function () {
        table.reload('tableThree', {
            method: 'get'
            , url: '/v2/user/selectUser'
            , headers: {
                "Cookie": document.cookie
            }
            , where: {
                'role': $("#role").val(),
                'userId': $('#userId').val(),
                'username': $('#username').val(),
                'name': $('#name').val()
            }
            , page: {
                curr: 1
            }
            , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
                if (res.data.length == 1) {
                    return {
                        "code": 0, //解析接口状态
                        "msg": "ok", //解析提示文本
                        "count": 1, //解析数据长度
                        "data": res.data //解析数据列表
                    };
                } else {
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
    });
});

// 增加用户
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
            success: function (res) {
                if (res.userMsg !== "") {
                    alert(res.userMsg);
                } else {
                    alert("发生未知错误，请重试");
                }
            },
            error: function () {
                alert("发生未知错误，请重试");
            }
        });
    }
}

// 修改用户
function updateMember() {
    var userId = $("#userId").val();
    var uname = $("#uname").val();
    var password = $("#password").val();
    if (password !== "") {
        password = hexMD5(password).toUpperCase();
    }
    var name = $("#name").val();
    $.ajax({
        url: '/v2/user/updateUser',
        type: 'post',
        headers: {
            "Cookie": document.cookie
        },
        data: {
            "userId": userId,
            "username": uname,
            "password": password,
            "name": name,
            "roleType": $('#roleType input[name="status"]:checked ').val()
        },
        success: function (res) {
            if (res.userMsg !== "") {
                alert(res.userMsg);
            } else {
                alert("发生未知错误，请重试");
            }
        },
        error: function () {
            alert("发生未知错误，请重试");
        }
    });
}

// 删除用户
function delMember() {
    layui.use('layer', function () {
        var $ = layui.jquery;
        // 删除操作
        layui.use(['table'], function () {
            var table = layui.table;
            table.on('tool(user)', function (obj) {
                var tr = obj.data;
                var msg = "您真的确定要删除吗？";
                if (confirm(msg) === true) {
                    $.ajax({
                        url: '/v2/user/deleteUser',
                        type: 'post',
                        headers: {
                            "Cookie": document.cookie
                        },
                        data: {
                            "userId": tr.id
                        },
                        success: function (res) {
                            if (res.userMsg !== "") {
                                alert(res.userMsg);
                                location.reload();
                            } else {
                                alert("发生未知错误，请重试");
                            }
                        },
                        error: function () {
                            alert("发生未知错误，请重试");
                        }
                    });
                } else {
                    return false;
                }
            })
        });
    })
}

// 从普通用户列表到修改用户跳转
function norUserToUpdateMember() {
    layui.use(['table'], function () {
        var table = layui.table;
        table.on('tool(user)', function (obj) {
            var tr = obj.data;
            window.localStorage.setItem("toOperateUserId", tr.id);
            window.localStorage.setItem("lastLocation", "http://localhost:8090/userList.html");
            window.location.href = "http://localhost:8090/updateMember.html";
        })
    });
}

// 从管理员列表到修改用户跳转
function adminToUpdateMember() {
    layui.use(['table'], function () {
        var table = layui.table;
        table.on('tool(admin)', function (obj) {
            var tr = obj.data;
            window.localStorage.setItem("toOperateUserId", tr.id);
            window.localStorage.setItem("lastLocation", "http://localhost:8090/adminList.html");
            window.location.href = "http://localhost:8090/updateMember.html";
        })
    });
}

// 从维修员列表到修改用户跳转
function repairmanToUpdateMember() {
    layui.use(['table'], function () {
        var table = layui.table;
        table.on('tool(repairman)', function (obj) {
            var tr = obj.data;
            window.localStorage.setItem("toOperateUserId", tr.id);
            window.localStorage.setItem("lastLocation", "http://localhost:8090/repairmanList.html");
            window.location.href = "http://localhost:8090/updateMember.html";
        })
    });
}

// 返回用户列表
function backToUserList() {
    window.location.href = window.localStorage.getItem("lastLocation");
}

// 管理员数据表格渲染
layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table;
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
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
                {field: 'id', title: '编号', width: 50, sort: true, fixed: 'left'}
                , {field: 'name', title: '姓名', width: 250}
                , {field: 'username', title: '用户名', width: 250}
                , {
                field: 'password', title: '密码', width: 250, templet: function (d) {
                    return d.password == '*' ? '**********' : '**********';
                }
            }
                , {
                field: 'roles', title: '权限', width: 250, templet: function (d) {
                    return d.roles == '' ? '管理员' : '管理员';
                }
            }
                , {title: '操作', align: 'center', width: 180, toolbar: '#admin'}
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
            } else {
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

    $('#searchAdmin').on('click', function () {
        table.reload('tableOne', {
            method: 'get'
            , url: '/v2/user/selectUser'
            , headers: {
                "Cookie": document.cookie
            }
            , where: {
                'userId': $('#userId').val(),
                'username': $('#username').val(),
                'name': $('#name').val()
            }
            , page: {
                curr: 1
            }
            , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
                if (res.data.length == 1) {
                    return {
                        "code": 0, //解析接口状态
                        "msg": "ok", //解析提示文本
                        "count": 1, //解析数据长度
                        "data": res.data //解析数据列表
                    };
                } else {
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
    });
});

// 维修员数据表格渲染
layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table;
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
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
                {field: 'id', title: '编号', width: 50, sort: true, fixed: 'left'}
                , {field: 'name', title: '姓名', width: 250}
                , {field: 'username', title: '用户名', width: 250}
                , {
                field: 'password', title: '密码', width: 250, templet: function (d) {
                    return d.password == '*' ? '**********' : '**********';
                }
            }
                , {
                field: 'roles', title: '权限', width: 250, templet: function (d) {
                    return d.roles == '' ? '维修员' : '维修员';
                }
            }
                , {title: '操作', align: 'center', width: 180, toolbar: '#repairman'}
            ]
        ]
        , id: 'tableTwo'
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
            } else {
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

    $('#searchRepairman').on('click', function () {
        table.reload('tableTwo', {
            method: 'get'
            , url: '/v2/user/selectUser'
            , headers: {
                "Cookie": document.cookie
            }
            , where: {
                'userId': $('#userId').val(),
                'username': $('#username').val(),
                'name': $('#name').val()
            }
            , page: {
                curr: 1
            }
            , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
                if (res.data.length == 1) {
                    return {
                        "code": 0, //解析接口状态
                        "msg": "ok", //解析提示文本
                        "count": 1, //解析数据长度
                        "data": res.data //解析数据列表
                    };
                } else {
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
    });
});