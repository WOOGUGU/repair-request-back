//搜索功能实现
function search(table,url,key,value)
{

    //这里以搜索为例
    table.reload({
        url:url,
        where:{
            key:value
        }
        ,page: {
            curr: 1 //重新从第 1 页开始
        }
        , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
            var result;
            if (this.page.curr) {
                result = res.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
            } else {
                result = res.slice(0, this.limit);
            }
            return {
                "code": "0",
                "msg": "ok",
                "count": res.length,
                "data": result
            };
        }
    });
}