Vue.component("review", {
    data: function () {
        return {
            user: {
                username: localStorage.getItem('username'),
                role: localStorage.getItem('role'),
                review: '',
            }
        }
    },
    template: `
   <div style="height: 81.7%;">
    <h1>Welcome <span style="color: seashell;">{{user.username}}</span></h1>
    <h2>we care about your oppinion...</h2>
        <div id="Div-panel">
            <form>
           <label class="label1" >Leave a comment:</label>
           <input v-model="review" type="text" placeholder="Leve a comment" name="comment" required> 

           <div id="center">
            <button class="button1" type="submit" v-on:click='comment()'>Login</button> 
            <button class="button1" v-on:click='cancel()' > Cancel</button> 
        </div>
        </form>
        </div>
</div>
  
    `,
    methods: {

    }
})