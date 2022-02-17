//汇总银行的js代码实现
layui.use(['table','form','layer'], function(){
    var table = layui.table;
    var $=layui.jquery;
    var form = layui.form;
    var layer=layui.layer
    //展示所有的交易信息数据
    table.render({
        elem: '#banksData'
        ,height: 600
        ,url: '/listBankOfTotalMoney'
        ,method:'get'
        ,where: {

        }
        ,cols: [
            [ //表头
                {field: 'bankName', title: '银行名称', width: 450}
                ,{field: 'money', title: '总余额', width: 450}
                ,{title: '操作', align:'center', toolbar: '#barDemo'}
            ]
        ]
        ,page: true //开启分页
        , limits: [3, 5, 10]  //一页选择显示3,5或10条数据
        , limit: 10  //一页显示10条数据
        , toolbar: '#toolbar'
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
});

function enterBank() {
    layui.use(['table'], function() {
        var table = layui.table;
        table.on('tool(bank)', function (obj) {
            var tr = obj.data;
            window.localStorage.setItem("bankName", tr.bankName);
            window.location.href = "http://localhost/bankTail.html";
        })
    });
}

function showDeal() {
    layui.use(['table'], function() {
        var table = layui.table;
        table.on('tool(bank)', function (obj) {
            var tr = obj.data;
            window.localStorage.setItem("id", tr.id);
            window.location.href = "http://localhost/cardTail.html";
        })
    });
}

function total() {
    $.ajax({
        url: '/totalMoney',
        type: 'get',
        headers: {
            'wrnm':localStorage.wrnm
        },
        data: {},
        success:function (res)
        {
            if(res.code!=200)
            {
                if (res.message !== "") {
                    alert(res.message);
                }
                else {
                    alert("出现异常，请重试");
                }
            }
            $("#total").html("<i class=\"iconfont left-nav-li\">&#xe70c;</i>汇总余额："+res.data+"");
        },
        error:function (res)
        {
            if (res.responseJSON === "") {
                alert("出现异常，请重试");
            } else {
                alert(res.responseJSON.message);
            }
        }
    });
}

function logout() {
    $.ajax({
        url: '/doLogout',
        type: 'get',
        headers: {
            "Cookie": document.cookie
        },
        data: {},
        success: function (res)
        {
            if(res.code === "00000") {
                alert(res.userMsg);
                location.assign("/login.html");
            } else {
                alert(res.userMsg);
            }
        },
        error: function () {
            alert("出现异常，请重试");
        }
    });
}