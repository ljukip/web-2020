Vue.component("profileUser", {
    data: function () {
        return {
            oldusername: localStorage.getItem('username'),
            user: {
                name: '',
                surname: '',
                gender: '',
                username: '',
                password: '',
                role: localStorage.getItem('role')
            },
            passwords: {
                password1: '',
                password2: ''
            },
            passwordBool: 'false',
            messageVal: ''
        }
    },
    template: `
        <div style="height: 81.7%;">
            <h1>Welcome <span style="color: seashell;">{{user.username}}</span></h1>
            <div id="Div-panel">
                <div><img  src="images/profile.png" width="66" height="66"> </div>

            <form @submit.prevent="">
                <div>
                    <div v-if="messageVal==='wrongName'" style="color:  #c41088;text-align: center;font-family: cursive;font-size: 21;">Wrong name format</div>
                        <label class="label1" style="color:#42022d;">Name : </label> 
                    <div id="center">
                        <input v-model="user.name" type="text" value="user.name"  name="name" required>
                    </div>
                    <div>
                        <div v-if="messageVal==='wrongSurname'" style="color:  #c41088;text-align: center;font-family: cursive;font-size: 21;">Wrong surname format</div>
                        <label class="label1">Surname : </label> 
                    </div>
                    <div id="center">
                        <input v-model="user.surname" type="text" value="user.surname" name="surname" required>
                    </div>
                    <div>
                        <div v-if="messageVal==='wrongGender'" style="color:  #c41088;text-align: center;font-family: cursive;font-size: 21;">Please select gender</div>
                        <label class="label1">Gender : </label> 
                    </div>
                    <div id="center">
                        <input type="radio" id="male" name="gender" value="male" v-model="user.gender" required>
                        <label for="male" class="label2">Male</label>
                        <input type="radio" id="female" name="gender" value="female" v-model="user.gender" required>
                        <label for="female" class="label2">Female</label>
                        <input type="radio" id="other" name="gender" value="other" v-model="user.gender" required>
                        <label for="other" class="label2">Other</label>
                    </div>
                    <br>
                    <div>
                        <label class="label1">Username : </label> 
                    </div>
                    <div id="center">
                        <input v-model="user.username" type="text" value="user.username" name="username" readonly>
                        <div style="color:  #c41088;text-align: center;font-family: cursive; font-size:11px;">username can not be changed</div>
                    </div>
                    <div>
                        <div v-if="messageVal==='wrongPassword'" style="color:  #c41088;text-align: center;font-family: cursive;font-size: 21;">Please enter a password</div>
                        <label  v-if="passwordBool==='allow'" class="label1">New password : </label>
                        <label  v-if="passwordBool!='allow'" class="label1">Password : </label> 
                    </div>
                    <div id="center" style="flex-direction: row;">
                        <input v-if="passwordBool!='allow'" v-model="user.password" type="password" value="user.password" name="password" v-on:click='passwordChange()' > 
                        <input v-if="passwordBool==='allow'" v-model="passwords.password2" type="password" name="password2" >      
                    </div>
                    <div> 
                        <label  v-if="passwordBool==='allow'" class="label1">Confirm password : </label>
                    </div>
                    <div id="center">
                        <input v-if="passwordBool==='allow'" v-model="passwords.password1" type="password" name="password1" >
                    </div>
                    <div v-if="messageVal==='wrongMatch'" style="color:  #c41055;text-align: center;font-family: cursive;font-size: 21;">Passwords do not match</div>
                    <div id="center" style="flex-direction: row;">
                        <button class="button1" type="submit" v-on:click='save()'>Save</button> 
                        <button class="button1" type="button" v-on:click='cancel()' > Cancel</button> 
                    </div>
                </div>
            </form>

            </div>
        </div>
    `,
    methods: {
        cancel: function () {
            Swal.fire({
                title: 'Are you sure you want to cancel changes?',
                text: "changes won't be saved!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#ffdff0',
                cancelButtonColor: '#c41088',
                confirmButtonText: 'Yes, Im sure!'
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.reload();
                }
            })
        },
        passwordChange: function () {
            this.passwordBool = 'allow';
            setTimeout(() => this.messageVal = '', 9000);
        },

        save: function () {
            console.log("in save");
            if (this.user.name == '') {
                console.log("wrong name");
                this.messageVal = 'wrongName';
                setTimeout(() => this.messageVal = '', 6000);
            }
            else if (this.user.surname == '') {
                this.messageVal = 'wrongSurname';
                setTimeout(() => this.messageVal = '', 6000);
            }
            else if (this.user.gender == '') {
                this.messageVal = 'wrongGender';
                setTimeout(() => this.messageVal = '', 6000);
            }
            else if (this.user.username == '') {
                this.messageVal = 'wrongUsername';
                setTimeout(() => this.messageVal = '', 6000);
            }
            else if (this.user.password == '') {
                this.messageVal = 'wrongPassword';
                setTimeout(() => this.messageVal = '', 6000);
            }
            else {
                if (this.passwords.password2 != '' && this.passwords.password2 != this.passwords.password1) {
                    console.log("not a match");
                    this.messageVal = 'wrongMatch';
                    setTimeout(() => this.messageVal = '', 6000);
                }
                else {
                    if (this.passwords.password2 != '') { this.user.password = this.passwords.password2 }
                    console.log("axois profile put username: " + this.user.username);
                    axios
                        .put(`rest/profileUser/${this.username}`, this.user)
                        .then(Response => {
                            console.log("updated");
                            this.messageVal = 'SuccesfullUpdate';
                            setTimeout(() => this.messageVal = '', 6000);
                            localStorage.setItem('username', this.user.username);
                            localStorage.setItem('password', this.user.password);
                            localStorage.setItem('role', this.user.role);

                            Swal.fire({
                                position: 'top-end',
                                icon: 'success',
                                title: 'Your work has been saved',
                                showConfirmButton: false,
                                timer: 3500
                            })
                            setTimeout(() => window.location.reload(), 3500);
                        })

                }
            }

        },
        failed: function () {
            console.log('nije uspelo');
            this.messageVal = 'ChangesFailed';
            setTimeout(() => this.messageVal = '', 6000);
        },

        load: function (data) {
            this.user.username = data.username;
            this.user.password = data.password;
            this.user.name = data.name;
            this.user.surname = data.surname;
            this.user.gender = data.gender;
            this.user.role = data.role;

        }

    },
    created() {

        if (!localStorage.getItem('jwt')) {
            this.$router.push('/login');
        }
        else {
            axios
                .get('rest/profileUser/' + this.oldusername)
                .then(response => this.load(response.data))
        }
    },
})