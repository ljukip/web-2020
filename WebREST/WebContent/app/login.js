Vue.component("login", {
    data: function () {
        return {
            user: { username: '', password: '' },
            error: false
        }
    },
    template: `
    <div>  
        <center> <h1> Login Form </h1> </center> 
        <form>
            <div class="container"> 
            <div>
            <label class="label1">Username : </label> 
            </div>
            <div id="center">
            <input v-model="user.username" type="text" placeholder="Enter Username" name="username" required>
            </div>
            <div>
                <label class="label1">Password : </label> 
            </div>
            <div id="center">
                <input v-model="user.password" type="password" placeholder="Enter Password" name="password" required>
            </div>
            <div id="center">
            <button class="button1" type="submit" v-on:click='login(user)'>Login</button> 
            <button class="button1" type="button" reset> Cancel</button> 
            </div>
            <p></p>
            <p></p>
            <div id="center">
            <a href="#">Forgotten password </a> </div>
            </div> 
        </form>   
    </div>   

    `,

    methods: {

        login: function (user) {
            console.log("1");
            axios //sending a request for a server to update the user, thus logging the user in
                //url-which is used for a request, data-data for update (post)
                .post("rest/login", {
                    username: user.username,
                    password: user.password
                })
                //handling systems responce (request answered=succes, ignored=failed)
                //if theres a responce (succes) then...
                //if not catch...
                .then((responce) => this.succes(responce.data))
                .catch(() => this.failed());
        },

        succes: function (data) {
            //if login was a succes, then jason object, username and role are being saved in a local storage(storing in browser) for further use without contanting the server
            //chek if theres a token (token is being set in the responce)
            if (!data.jwt) {
                this.loginFailed();
                return;
            }
            localStorage.setItem("username", data.username);
            localStorage.setItem("password", data.password);
            localStorage.setItem("role", data.role);
            localStorage.setItem("jwt", data.jwt);

            this.error = false;
            this.$router.push("/");
            window.location.reload(); //load pushed
        },

        failed: function () {
            this.error = true;
        },


    },
});
