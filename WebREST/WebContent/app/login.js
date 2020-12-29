Vue.component("login", {
    data: function () {
        return {
            user: { username: '', password: '', role: '' },
            error: false
        }
    },
    template: `
    <div>  
        <center> <h1 style="color:rgb(82, 7, 63);font-family: cursive;"> login<br></h1> </center> 
        <div id="div-fit">
            <div class="form-space">
                <form @submit.prevent="">
                    <div class="container"> 
                    <br> 
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
                        <div v-if="error" style="color:  #c41088;text-align: center;font-family: cursive;font-size: 21;">Wrong username or password</div>
                        <button class="button1" type="submit" v-on:click='login(user)'>Login</button> 
                        <button class="button1" v-on:click='cancel()' > Cancel</button> 
                    </div>
                    <p></p>
                    <p></p>
                    <div id="center">
                    <a href="#">Forgotten password </a> </div>
                    </div> 
                </form> 
            </div>
        </div>  
    </div>   

    `,

    methods: {

        login: function (user) {

            axios //sending a request for a server to update the user, thus logging the user in
                //url-which is used for a request, data-data for update (post)
                .post('rest/login', {
                    username: user.username,
                    password: user.password
                })
                .then((responce) => this.succes(responce.data))
                .catch(() => this.failed());
        },

        succes: function (data) {
            //if login was a succes, then jason object, username and role are being saved in a local storage(storing in browser) for further use without contanting the server
            //chek if theres a token (token is being set in the responce)
            if (!data.jwt) {
                console.log("proverava jwt");
                this.failed();
                return;
            }
            console.log("succes");
            localStorage.setItem('username', data.username);
            localStorage.setItem('password', data.password);
            localStorage.setItem('role', data.role);
            localStorage.setItem('jwt', data.jwt);

            this.error = false;
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Login succesfull',
                showConfirmButton: false,
                timer: 1600
            })
            setTimeout(this.$router.push('/homeUser'), 2100);
            setTimeout(window.location.reload(), 2100); //load pushed
        },
        cancel: function () {
            Swal.fire({
                title: 'Are you sure you want to cancel changes?',
                text: "Registration will be canceled",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#ffdff0',
                cancelButtonColor: '#c41088',
                confirmButtonText: 'Yes, Im sure!'
            }).then((result) => {
                if (result.isConfirmed) {
                    this.$router.push('/');
                    window.location.reload();
                }
            })
        },

        failed: function () {
            console.log("bad request");
            localStorage.removeItem("jwt");
            this.error = true;
            console.log(this.error);
            setTimeout(() => this.error = false, 6000);
        }
        //to do: validation

    },
});
