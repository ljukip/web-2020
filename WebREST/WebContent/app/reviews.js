Vue.component("reviews", {
    data: function () {
        return {
            review: { value: 'proba1', isPublished: 'false' },
            reviews: [],
            role: localStorage.getItem('role'),
            username: localStorage.getItem('username'),

        }
    },
    template: `

    <div style="height: 81.7%;">
    <nav style="background-color: lavenderblush;">
        <hr style='background:#c41088;height:4px;'>
        <label class="label1">All reviews</label>
        <hr style='background:#c41088;height:4px;'>
    </nav>
    <div id="Div-panel" style="display: inline;">
            <div>
                <table class="myTable">
                    <thead>
                        <tr class="header">
                            <th>review</th>
                            <th v-if="role==='HOST'">publish</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-bind:key='review.value' v-for='review in reviews'>
                            <td>{{review.review}}<br>
                            <star-rating inactive-color="#e9c4d8"
                            active-color="rgb(201, 28, 158)"
                            glow:2 v-bind:read-only="true"
                                v-bind:star-size="20" v-bind:show-rating="false" v-bind:rating="review.rating">
                            </star-rating>
                            <br>guest: {{review.guestId}}<br>apartment ID: {{review.apartmentId}}</td>
                            <td v-if="role==='HOST'">
                                <button v-if="!review.published" class="buttonChoose" style="background-image: url('./images/publish.png');" v-on:click= "publish(review.id)" type="button"></button>
                                <p v-if="review.published" class="buttonChoose" style="background-image: url('./images/cnc.png');" v-on:click= "unpublish(review.id)" type="button"></p>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    `,
    methods: {
        publish(id) {
            Swal.fire({
                title: 'Are you sure you want to publish the feedback ?',
                text: "Feedback will be published",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#ffdff0',
                cancelButtonColor: '#c41088',
                confirmButtonText: 'Yes, Im sure!'
            }).then((result) => {
                if (result.isConfirmed) {
                    //block user
                    axios
                        .put(`rest/review/publish/` + id)
                        .then(Response => {

                            Swal.fire({
                                position: 'top-end',
                                icon: 'success',
                                title: 'Feedback has been published!',
                                showConfirmButton: false,
                                timer: 3500
                            })
                            setTimeout(() => window.location.reload(), 3500);
                        })

                }
            })
        },
        unpublish(id) {
            Swal.fire({
                title: 'Are you sure you want to take down the feedback ?',
                text: "Feedback will be taken down",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#ffdff0',
                cancelButtonColor: '#c41088',
                confirmButtonText: 'Yes, Im sure!'
            }).then((result) => {
                if (result.isConfirmed) {
                    axios
                        .put(`rest/review/unpublish/` + id)
                        .then(Response => {

                            Swal.fire({
                                position: 'top-end',
                                icon: 'success',
                                title: 'Feedback has been taken down!',
                                showConfirmButton: false,
                                timer: 3500
                            })
                            setTimeout(() => window.location.reload(), 3500);
                        })

                }
            })
        },
        getAllReviews() {
            axios
                .get('rest/review/' + this.role + '/' + this.username)
                .then(Response => (this.reviews = Response.data));
        },


    },
    created() {


        if (localStorage.getItem('jwt') == '') {
            this.$router.push('/login');

        }
        this.getAllReviews();
    },
})