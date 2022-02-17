//交易信息相关的js代码实现
layui.use(['table','form','layer'], function(){
    var table = layui.table;
    //展示所有的交易信息数据
    table.render({
        elem: '#DealData'
        ,height: 600
        ,url: '/deals'
        ,method:'get'
        ,where: {

        }
        ,cols: [
            [ //表头
                {field: 'id', title: '交易编号', width:250, sort: true, fixed: 'left'}
                ,{field: 'remark', title: '注明', width: 250}
                ,{field: 'paytime', title: '到账时间',width: 250, templet:function(d){
                    return layui.util.toDateString(d.paytime,'yyyy-MM-dd HH:mm:ss');
                }}
                ,{field: 'company', title: '业务相关单位',width: 250}
                ,{field: 'handler', title: '汇款单位经办人',width: 250}
                ,{field: 'payee', title: '收款单位',width: 250}
                ,{field: 'receiver', title: '收款人',width: 250}
                ,{field: 'item', title: '用项',width: 250}
                ,{field: 'detail', title: '品名',width: 250}
                ,{field: 'payType', title: '收支',width: 250, templet:function(d){
                    return d.payType==1?'收入':'支出';}}
                ,{field: 'bankName', title: '银行名称',width: 250}
                ,{field: 'num', title: '卡号',width: 250}
                ,{field: 'money', title: '金额',width: 250}
                ,{field: 'cash', title: '现金收支',width: 250}
                ,{field: 'computerBalance', title: '电脑余额',width: 250}
                ,{field: 'infoBalance', title: '信息余额',width: 250}
                ,{field: 'reduceBalance', title: '余额差',width: 250}
                ,{field: 'receivePeo', title: '收货人',width: 250}
                ,{field: 'byPeo', title: '经办人',width: 250}
                ,{field: 'unitPrice', title: '单价',width: 250}
                ,{field: 'number', title: '数量',width: 250}
                ,{field: 'unit', title: '单位',width: 250}
                ,{field: 'expectMoney', title: '应收金额',width: 250}
                ,{field: 'realMoney', title: '实收金额',width: 250}
                ,{field: 'leftMoney', title: '余款',width: 250}
                ,{field: 'infoMark', title: '备注',width: 250}
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
});

function searchData() {
    layui.use(['table','form','layer'], function() {
        var table = layui.table;
        var pram = new Object();

        var bankName = $("#bankName").val();
        var bid = $("#bid").val();
        var byPeo = $("#byPeo").val();
        var company = $("#company").val();
        var computerBalance = $("#computerBalance").val();
        var detail = $("#detail").val();
        var payType = $("#payType").val();
        var expectMoney = $("#expectMoney").val();
        var handler = $("#handler").val();
        var id = $("#id").val();
        var infoBalance = $("#infoBalance").val();
        var infoMark = $("#infoMark").val();
        var item = $("#item").val();
        var leftMoney = $("#leftMoney").val();
        var money = $("#money").val();
        var num = $("#num").val();
        var number = $("#number").val();
        var payee = $("#payee").val();
        var paytime = $("#paytime").val();
        var realMoney = $("#realMoney").val();
        var receivePeo = $("#receivePeo").val();
        var receiver = $("#receiver").val();
        var reduceBalance = $("#reduceBalance").val();
        var remark = $("#remark").val();
        var unit = $("#unit").val();
        var unitPrice = $("#unitPrice").val();
        if (company !== "") {
            pram.company = company;
        }
        if (bankName !== "") {
            pram.bankName = bankName;
        }
        if (bid !== "") {
            pram.bid = bid;
        }
        if (byPeo !== "") {
            pram.byPeo = byPeo;
        }
        if (computerBalance !== "") {
            pram.computerBalance = computerBalance;
        }
        if (detail !== "") {
            pram.detail = detail;
        }
        if (payType !== "") {
            pram.payType = payType;
        }
        if (expectMoney !== "") {
            pram.expectMoney = expectMoney;
        }
        if (handler !== "") {
            pram.handler = handler;
        }
        if (id !== "") {
            pram.id = id;
        }
        if (infoBalance !== "") {
            pram.infoBalance = infoBalance;
        }
        if (infoMark !== "") {
            pram.infoMark = infoMark;
        }
        if (item !== "") {
            pram.item = item;
        }
        if (leftMoney !== "") {
            pram.leftMoney = leftMoney;
        }
        if (money !== "") {
            pram.money = money;
        }
        if (num !== "") {
            pram.num = num;
        }
        if (number !== "") {
            pram.number = number;
        }
        if (payee !== "") {
            pram.payee = payee;
        }
        if (paytime !== "") {
            pram.paytime = paytime;
        }
        if (realMoney !== "") {
            pram.realMoney = realMoney;
        }
        if (receivePeo !== "") {
            pram.receivePeo = receivePeo;
        }
        if (receiver !== "") {
            pram.receiver = receiver;
        }
        if (reduceBalance !== "") {
            pram.reduceBalance = reduceBalance;
        }
        if (remark !== "") {
            pram.remark = remark;
        }
        if (unit !== "") {
            pram.unit = unit;
        }
        if (unitPrice !== "") {
            pram.unitPrice = unitPrice;
        }

        table.reload('tableOne', {
            method: 'get'
            , url: '/getByExample'
            , where: pram
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
}

function addDeal() {
    var pram = new Object();
    var bank = JSON.parse(window.localStorage.getItem("bank"));
    var index = $("#bankName").val();

    var byPeo = $("#byPeo").val();
    var company = $("#company").val();
    var computerBalance = $("#computerBalance").val();
    var detail = $("#detail").val();
    var payType = $("#payType").val();
    var expectMoney = $("#expectMoney").val();
    var handler = $("#handler").val();
    var id = $("#id").val();
    var infoBalance = $("#infoBalance").val();
    var infoMark = $("#infoMark").val();
    var item = $("#item").val();
    var leftMoney = $("#leftMoney").val();
    var money = $("#money").val();
    var num = $("#num").val();
    var number = $("#number").val();
    var payee = $("#payee").val();
    var paytime = $("#paytime").val();
    var realMoney = $("#realMoney").val();
    var receivePeo = $("#receivePeo").val();
    var receiver = $("#receiver").val();
    var reduceBalance = $("#reduceBalance").val();
    var remark = $("#remark").val();
    var unit = $("#unit").val();
    var unitPrice = $("#unitPrice").val();
    if (remark === "") {
        alert("注名不能为空");
        return;
    }
    if (paytime === "") {
        alert("到账时间不能为空");
        return;
    }
    if (company === "") {
        alert("业务相关单位不能为空");
        return;
    }
    if (handler === "") {
        alert("汇款单位经办人不能为空");
        return;
    }
    if (receiver === "") {
        alert("收款人不能为空");
        return;
    }
    if (payee === "") {
        alert("收款单位不能为空");
        return;
    }
    if (detail === "") {
        alert("品名不能为空");
        return;
    }
    // if (bankName === "") {
    //     alert("银行名称不能为空");
    //     return;
    // }
    // if (num === "") {
    //     alert("卡号不能为空");
    //     return;
    // }
    if (money === "") {
        alert("金额不能为空");
        return;
    }
    if (company !== "") {
        pram.company = company;
    }
    // if (bankName !== "") {
    //     pram.bankName = bankName;
    // }
    // if (bid !== "") {
    //     pram.bid = bid;
    // }
    if (byPeo !== "") {
        pram.byPeo = byPeo;
    }
    if (computerBalance !== "") {
        pram.computerBalance = computerBalance;
    }
    if (detail !== "") {
        pram.detail = detail;
    }
    if (payType !== "") {
        pram.payType = payType;
    }
    if (expectMoney !== "") {
        pram.expectMoney = expectMoney;
    }
    if (handler !== "") {
        pram.handler = handler;
    }
    if (id !== "") {
        pram.id = id;
    }
    if (infoBalance !== "") {
        pram.infoBalance = infoBalance;
    }
    if (infoMark !== "") {
        pram.infoMark = infoMark;
    }
    if (item !== "") {
        pram.item = item;
    }
    if (leftMoney !== "") {
        pram.leftMoney = leftMoney;
    }
    if (money !== "") {
        pram.money = money;
    }
    if (num !== "") {
        pram.num = num;
    }
    if (number !== "") {
        pram.number = number;
    }
    if (payee !== "") {
        pram.payee = payee;
    }
    if (paytime !== "") {
        pram.paytime = paytime;
    }
    if (realMoney !== "") {
        pram.realMoney = realMoney;
    }
    if (receivePeo !== "") {
        pram.receivePeo = receivePeo;
    }
    if (receiver !== "") {
        pram.receiver = receiver;
    }
    if (reduceBalance !== "") {
        pram.reduceBalance = reduceBalance;
    }
    if (remark !== "") {
        pram.remark = remark;
    }
    if (unit !== "") {
        pram.unit = unit;
    }
    if (unitPrice !== "") {
        pram.unitPrice = unitPrice;
    }
    for (var i = 0; i < bank.length; i++) {
        if (index == bank[i].id) {
            pram.bankName = bank[i].bankName;
            pram.num = bank[i].num;
            pram.bid = bank[i].id;
        }
    }

    $.ajax({
        url: '/deals',
        type: 'post',
        contentType: 'application/x-www-form-urlencoded',
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

function delDeal() {
    $.ajax({
        url: '/deals',
        type: 'delete',
        headers: {
            'wrnm': localStorage.wrnm
        },
        data: {
            "id": $("#id").val(),
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