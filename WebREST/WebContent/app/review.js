Vue.component("review", {
    data: function () {
        return {
            apId: localStorage.getItem('reviewApartmentId'),
            user: {
                username: localStorage.getItem('username'),
                role: localStorage.getItem('role'),
            },
            review: '',
            star: null
        }
    },
    template: `<div style="height: 81.7%;">
    <h1>Welcome <span style="color: seashell;">{{user.username}}</span></h1>
    <h2>we care about your oppinion...</h2>
    <div id="Div-panel">
        <form  @submit.prevent="">
            <label class="label1">Leave a comment:</label>
            <input v-model="review" style="width: 87%;" type="text" placeholder="Leve a comment" name="comment" required>
            <star-rating
            inactive-color="#e9c4d8"
            active-color="rgb(201, 28, 158)"
            glow:2
            glow-color="#ffdff0"
            v-model="star"></star-rating>

            <div id="center">
                <button class="button1" type="submit" v-on:click='comment()'>Send</button>
                <button class="button1" v-on:click='cancel()'> Cancel</button>
            </div>
        </form>
    </div>
</div>`,
    methods: {
        comment() {
            console.log("review:" + this.apId + this.username);
            axios
                .post('rest/review', {
                    guestId: this.user.username,
                    apartmentId: this.apId,
                    review: this.review,
                    rating: this.star,
                })
                .then((responce) => this.succes(responce.data))
                .catch(() => this.failed());

        },
        succes(data) {
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Feedback has been saved',
                showConfirmButton: false,
                timer: 1400
            })
            localStorage.removeItem("reviewApartmentId");
            this.$router.push('/');
        },
        failed() {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Something went wrong!',
            })
        }
    },
})