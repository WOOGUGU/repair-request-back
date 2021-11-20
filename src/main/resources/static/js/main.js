// 用户相关操作
{
    // 添加用户
    function submitAddUser() {
        let username = $("#username").val();
        let password = $("#password").val();
        let name = $("#name").val();

        $.ajax({
            type: "post",
            url: "/addUser",
            data: {
                'username': username,
                'password': password,
                'name': name
            },
            success: function (res) {
                if (res.status === 'handle_success') {
                    alert("添加成功！");
                }
                else {
                    alert("出现异常，请重试！");
                }
            }
        })
    }

    // 删除用户
    function submitDeleteUser() {
        let id = $("#user_id").val();
        $.ajax({
            type: "post",
            url: "/deleteUser",
            data: {
                'id': id
            },
            success: function (res) {
                if (res.status === 'handle_success') {
                    alert("删除成功！");
                }
                else if (res.status === 'data_not_exist') {
                    alert("此用户不存在，请输入正确的用户名！");
                }
                else {
                    alert("出现异常，请重试！");
                }
            }
        })
    }

    // 通过用户名查找用户
    function submitSelectUserByUsername() {
        let username = $("#user_username").val();
        $.ajax({
            type: "post",
            url: "/selectUserByUsername",
            data: {
                'username': username
            },
            success: function (res) {
                if (res.status === 'handle_success') {
                    alert("查询成功！");
                }
                else if (res.status === 'data_not_exist') {
                    alert("此用户不存在，请输入正确的用户名！");
                }
                else {
                    alert("出现异常，请重试！");
                }
            }
        })
    }

    // 更新用户数据
    function submitUpdateUser() {
        let user = {
            'id': $("#update_id").val(),
            'username': $("#update_username").val(),
            'password': $("#update_password").val(),
            'name': $("#update_name").val()
        }

        $.ajax({
            type: "post",
            url: "/updateUser",
            data: {
                'id': user.id,
                'username': user.username,
                'password': user.password,
                'name': user.name
            },
            success: function (res) {
                if (res.status === 'handle_success') {
                    alert("修改成功！");
                }
                else if (res.status === 'data_not_exist') {
                    alert("此用户不存在，请输入正确的用户名！");
                }
                else {
                    alert("出现异常，请重试！");
                }
            }
        })
    }

    // 查询所有用户
    function submitSelectAllUser() {
        $.ajax({
            type: "post",
            url: "/selectAllUser",
            data: {},
            success: function (res) {
                if (res.status === 'data_not_exist') {
                    alert("当前没有用户，请添加用户！");
                }
                else if (res.status === 'handle_success') {
                    alert("查询成功！");
                }
                else {
                    alert("出现异常，请重试！");
                }
            }
        })
    }
}

// 管理员相关操作
{
    // 添加管理员
    function submitAddAdmin() {
        let username = $("#admin_username").val();
        let password = $("#admin_password").val();
        let name = $("#admin_name").val();

        $.ajax({
            type: "post",
            url: "/addAdmin",
            data: {
                'username': username,
                'password': password,
                'name': name
            },
            success: function (res) {
                if (res.status === 'handle_success') {
                    alert("添加成功！");
                }
                else {
                    alert("出现异常，请重试！");
                }
            }
        })
    }

    // 删除管理员
    function submitDeleteAdmin() {
        let id = $("#user_id").val();
        $.ajax({
            type: "post",
            url: "/deleteAdmin",
            data: {
                'id': id
            },
            success: function (res) {
                if (res.status === 'handle_success') {
                    alert("删除成功！");
                }
                else if (res.status === 'data_not_exist') {
                    alert("此用户不存在，请输入正确的用户名！");
                }
                else {
                    alert("出现异常，请重试！");
                }
            }
        })
    }

    // 通过用户名查找管理员
    function submitSelectAdminByUsername() {
        let username = $("#adminUsername").val();
        $.ajax({
            type: "post",
            url: "/selectAdminByUsername",
            data: {
                'username': username
            },
            success: function (res) {
                if (res.status === 'handle_success') {
                    alert("查询成功！");
                }
                else if (res.status === 'data_not_exist') {
                    alert("此用户不存在，请输入正确的用户名！");
                }
                else {
                    alert("出现异常，请重试！");
                }
            }
        })
    }

    // 更新管理员数据
    function submitUpdateAdmin() {
        let admin = {
            'id': $("#update_admin_id").val(),
            'username': $("#update_admin_username").val(),
            'password': $("#update_admin_password").val(),
            'name': $("#update_admin_name").val()
        }

        $.ajax({
            type: "post",
            url: "/updateAdmin",
            data: {
                'id': admin.id,
                'username': admin.username,
                'password': admin.password,
                'name': admin.name
            },
            success: function (res) {
                if (res.status === 'handle_success') {
                    alert("修改成功！");
                }
                else if (res.status === 'data_not_exist') {
                    alert("此用户不存在，请输入正确的用户名！");
                }
                else {
                    alert("出现异常，请重试！");
                }
            }
        })
    }

    // 查询所有管理员
    function submitSelectAllAdmin() {
        $.ajax({
            type: "post",
            url: "/selectAllAdmin",
            data: {},
            success: function (res) {
                if (res.status === 'data_not_exist') {
                    alert("当前没有用户，请添加用户！");
                }
                else if (res.status === 'handle_success') {
                    alert("查询成功！");
                }
                else {
                    alert("出现异常，请重试！");
                }
            }
        })
    }
}

// 处理登录请求
{
    function submitLogin() {
        let login = {
            username: $("#login_username").val(),
            password: $("#login_password").val()
        }
        console.log(login);
        let login_json = JSON.stringify(login);
        console.log(login_json);
        $.ajax({
            type: "post",
            url: "/handleLogin",
            contentType: "application/json",
            data: login_json,
            success: function (res) {
                console.log(res);
            }
        })
    }
}

// 工单逻辑处理
{
    // 添加工单
    function submitAddOrder() {
        let order = {
            sender: $("#add_sender").val(),
            tel: $("#add_tel").val(),
            type: $("#add_type").val(),
            des: $("#add_des").val(),
            position: $("#add_position").val(),
            timeSubscribe: $("#add_timeSubscribe").val(),
            progress: $("#add_progress").val(),
            solver: $("#add_solver").val(),
            timeStart: $("#add_timeStart").val(),
            timeDistribution: $("#add_timeDistribution").val(),
            timeEnd: $("#add_timeEnd").val(),
            feedBack: $("#add_feedBack").val()
        }
        console.log(order);
        let order_json = JSON.stringify(order);
        console.log(order_json);

        $.ajax({
            type: "post",
            url: "/addOrder",
            contentType: "application/json",
            data: order_json,
            success: function (res) {
                console.log(res);
            }
        })
    }

    // 删除工单
    function submitDeleteOrder() {
        $.ajax({
            type: "post",
            url: "/deleteOrder",
            data: {
                'id': $("#delete_id").val()
            },
            success: function (res) {
                if (res.status === 'handle_success') {
                    alert("删除成功！");
                }
                else if (res.status === 'data_not_exist') {
                    alert("此用户不存在，请输入正确的用户名！");
                }
                else {
                    alert("出现异常，请重试！");
                }
            }
        })
    }

    // 根据id查找工单
    function submitSelectOrderById() {
        $.ajax({
            type: "post",
            url: "/selectOrderById",
            data: {
                'id': $("#select_id").val()
            },
            success: function (res) {
                if (res.status === 'handle_success') {
                    alert("查找成功！");
                }
                else if (res.status === 'data_not_exist') {
                    alert("此用户不存在，请输入正确的用户名！");
                }
                else {
                    alert("出现异常，请重试！");
                }
            }
        })
    }

    // 查找所有用户的工单
    function submitSelectAllOrder() {
        $.ajax({
            type: "post",
            url: "/selectAllOrder",
            data: {},
            success: function (res) {
                if (res.status === 'handle_success') {
                    alert("查找成功！");
                }
                else if (res.status === 'data_not_exist') {
                    alert("此用户不存在，请输入正确的用户名！");
                }
                else {
                    alert("出现异常，请重试！");
                }
            }
        })
    }
}