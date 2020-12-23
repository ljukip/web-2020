Vue.component("homeUser", {
    data: function () {
        return {
            user: {
                username: localStorage.getItem('username'),
                role: localStorage.getItem('role')
            }
        }
    },
    template: `
   <div style="height: 81.7%;">
    <h1>Welcome <span style="color: seashell;">{{user.username}}</span></h1>
        <div id="Div-panel">
            <div style="flex-direction: row;">
                <button class="oval" style="background-image: url('images/for_rent.jpg');display: inline-block;"><p></p> </button>
                <button class="oval" style="background-image: url('images/res.png');display: inline-block; "><p></p></button>
                <button class="oval" style="background-image: url('images/profile.png');display: inline-block; "><p> </p></button>
            </div>
            <div style="flex-direction: row;">
                <label class="label1" style="width: 40%;">apartments</label>
                <label class="label1"  style="width: 20%;">reservations</label>
                <label>  </label>
                <label class="label1"  style="width: 38%;">profile</label>
            </div>
        </div>
</div>
  
    `,
    methods: {

    }
})