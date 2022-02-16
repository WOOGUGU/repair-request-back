//业务单位交易的js代码实现
layui.use(['table','form','layer'], function(){
    var table = layui.table;
    var $=layui.jquery;
    var form = layui.form;
    var layer=layui.layer
    //展示所有的交易信息数据
    table.render({
        elem: '#businessData'
        ,height: 600
        ,url: '/getAllCompanyDebts'
        ,method:'get'
        ,where: {

        }
        ,cols: [
            [ //表头
                {field: 'id', title: '序号', width:250, sort: true, fixed: 'left'}
                ,{field: 'debt', title: '欠款', width: 250}
                ,{field: 'companyName', title: '业务相关单位',width: 550}
                ,{fixed: 'right',title: '操作', align:'center', toolbar: '#barDemo'}
            ]
        ]
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
});

function business() {
    layui.use(['table'], function() {
        var table = layui.table;
        table.on('tool(business)', function (obj) {
            var tr = obj.data;
            window.localStorage.setItem("companyName", tr.companyName);
            window.location.href = "http://localhost/company.html";
        })
    });
}

function addBank(type) {
    var pram = new Object();
    if (type === 1) {
        var bankName = $("#bankName").val();
        var computerBalance = $("#computerBalance").val();
        var infoBalance = $("#infoBalance").val();
        var num = $("#num").val();

        if (bankName === "") {
            alert("银行名称不能为空");
            return;
        }
        if (computerBalance === "") {
            alert("电脑余额不能为空");
            return;
        }
        if (infoBalance === "") {
            alert("信息余额不能为空");
            return;
        }
        if (num.length != 4) {
            alert("卡号只能输入后四位");
            return;
        }
        pram.bankName = bankName;
        pram.computerBalance = computerBalance;
        pram.infoBalance = infoBalance;
        pram.num = num;
    } else {
        var computerBalance = $("#computerBalance").val();
        if (computerBalance === "") {
            alert("电脑余额不能为空");
            return;
        }
        pram.bankName = "现金";
        pram.computerBalance = computerBalance;
    }

    $.ajax({
        url: '/banks',
        type: 'post',
        headers: {
            'wrnm':localStorage.wrnm
        },
        data: pram,
        success:function (res)
        {
            if(res.code == 200)
            {
                alert("添加成功");
            } else {
                if (res.message !== "") {
                    alert(res.message);
                }
                else {
                    alert("出现异常，请重试");
                }
            }
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


