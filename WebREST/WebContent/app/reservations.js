Vue.component("reservations", {
    data: function () {
        return {
            reservationsCreated: [],
            reservationsCompleted: [],
            reservationsAccepted: [],
            reservationsDeclined: [],
            reservationsCanceled: [],
            reservations: [],
            reservation: '',
            guest: '', //get guest by id from res
            allowComplete: 'false',
            role: localStorage.getItem("role"),
            status: '',
            today: new Date(),
        }
    },
    template: `
    <div style="height: 81.7%;">
    <nav style="background-color: lavenderblush;">
        <hr style='background:#c41088;height:4px;'>
        <label class="label1">Created reservations</label>
        <hr style='background:#c41088;height:4px;'>
    </nav>
    <div id="Div-panel" style="display: inline;">
            <div>
                <table class="myTable">
                    <thead>
                        <tr class="header">
                            <th>reservations</th>
                            <th  v-if="role==='HOST'">accept</th>
                            <th  v-if="role==='HOST'">decline</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-bind:key='reservation.id' v-for='reservation in reservationsCreated'>
                            <td>
                                Guest: {{reservation.guestId}}<br>
                                ApartmentID: {{reservation.apartmentId}}<br>
                                From: {{new Date(reservation.from)}}<br>
                                To: {{new Date(reservation.to)}}<br>
                                No. of nights: {{reservation.night}}<br>
                                Price: {{reservation.price}}<br>
                            </td>
                            <td  v-if="role==='HOST'"><button class="buttonChoose" v-on:click= "accept(reservation.id)" type="button"></button></td>
                            <td  v-if="role==='HOST'"><button class="buttonChoose" style="background-image: url('./images/cnc.png');" v-on:click= "decline(reservation.id)" type="button"></button></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        <nav style="background-color: lavenderblush;">
            <hr style='background:#c41088;height:4px;'>
            <label class="label1">Accepted reservatios</label>
            <hr style='background:#c41088;height:4px;'>
        </nav>

        <div>
            <table class="myTable">
                <thead>
                    <tr class="header">
                        <th>reservations</th>
                        <th  v-if="role==='HOST'">complete</th>
                        <th  v-if="role==='GUEST'">Cancel</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-bind:key='reservation.id' v-for='reservation in reservationsAccepted'>
                        <td>
                            Guest: {{reservation.guestId}}<br>
                            ApartmentID: {{reservation.apartmentId}}<br>
                            From: {{new Date(reservation.from)}}<br>
                            To: {{new Date(reservation.to)}}<br>
                            No. of nights: {{reservation.night}}<br>
                            Price: {{reservation.price}}<br>
                        </td>
                        <td v-if="role==='HOST'"><button class="buttonChoose" v-on:click= "complete(reservation.id,reservation.to)" type="button"></button></td>
                        <td  v-if="role==='GUEST'"><button class="buttonChoose" style="background-image: url('./images/cnc.png');" v-on:click= "cancel(reservation.id)" type="button"></button></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

        <nav style="background-color: lavenderblush;">
            <hr style='background:#c41088;height:4px;'>
            <label class="label1">Declined reservatins</label>
            <hr style='background:#c41088;height:4px;'>
        </nav>
            <div>
                <table class="myTable">
                    <thead>
                        <tr class="header">
                            <th>reservations</th>
                            <th v-if="role==='GUEST'">Review</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-bind:key='reservation.id' v-for='reservation in reservationsDeclined'>
                            <td>
                                Guest: {{reservation.guestId}}<br>
                                ApartmentID: {{reservation.apartmentId}}<br>
                                From: {{new Date(reservation.from)}}<br>
                                To: {{new Date(reservation.to)}}<br>
                                No. of nights: {{reservation.night}}<br>
                                Price: {{reservation.price}}<br>
                            </td>
                            <td v-if="role==='GUEST'"><button class="buttonChoose" style="background-image: url('./images/review.png');" v-on:click= "review(reservation.id)" type="button"></button></td>
                        </tr>
                    </tbody>
                </table>
            </div>

        <nav style="background-color: lavenderblush;">
            <hr style='background:#c41088;height:4px;'>
            <label class="label1">Completed reservatins</label>
            <hr style='background:#c41088;height:4px;'>
        </nav>
            <div>
                <table class="myTable">
                    <thead>
                        <tr class="header">
                            <th>reservations</th>
                            <th v-if="role==='GUEST'">Review</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-bind:key='reservation.id' v-for='reservation in reservationsCompleted'>
                            <td>
                                Guest: {{reservation.guestId}}<br>
                                ApartmentID: {{reservation.apartmentId}}<br>
                                From: {{new Date(reservation.from)}}<br>
                                To: {{new Date(reservation.to)}}<br>
                                No. of nights: {{reservation.night}}<br>
                                Price: {{reservation.price}}<br>
                            </td>
                            <td v-if="role==='GUEST'"><button class="buttonChoose" style="background-image: url('./images/review.png');" v-on:click= "review(reservationid)" type="button"></button></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        <nav style="background-color: lavenderblush;">
            <hr style='background:#c41088;height:4px;'>
            <label class="label1">Canceled reservatins</label>
            <hr style='background:#c41088;height:4px;'>
        </nav>
            <div>
                <table class="myTable">
                    <thead>
                        <tr class="header">
                            <th>reservations</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-bind:key='reservation.id' v-for='reservation in reservationsCanceled'>
                            <td>
                                Guest: {{reservation.guestId}}<br>
                                ApartmentID: {{reservation.apartmentId}}<br>
                                From: {{new Date(reservation.from)}}<br>
                                To: {{new Date(reservation.to)}}<br>
                                No. of nights: {{reservation.night}}<br>
                                Price: {{reservation.price}}<br>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    `,
    methods: {
        cancel(id) {
            this.status = 'canceled';
            Swal.fire({
                title: 'Are you sure you want to cancel the reservation?',
                text: "Reservation will be canceled",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#ffdff0',
                cancelButtonColor: '#c41088',
                confirmButtonText: 'Yes, Im sure!'
            }).then((result) => {
                if (result.isConfirmed) {
                    axios
                        .put(`rest/reservations/changeStatus/` + id + '/' + this.status)
                        .then(Response => {

                            Swal.fire({
                                position: 'top-end',
                                icon: 'success',
                                title: 'Reservation has been canceled!',
                                showConfirmButton: false,
                                timer: 3500
                            })
                            setTimeout(() => window.location.reload(), 3500);
                        })
                }
            })
        },
        review(id) {
            this.$router.push('/review');
            //axios  + appointment.id

        },
        accept(id) {
            this.status = 'accepted';
            Swal.fire({
                title: 'Are you sure you want to accept the reservation?',
                text: "Reservation will be accepted",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#ffdff0',
                cancelButtonColor: '#c41088',
                confirmButtonText: 'Yes, Im sure!'
            }).then((result) => {
                if (result.isConfirmed) {
                    axios
                        .put(`rest/reservations/changeStatus/` + id + '/' + this.status)
                        .then(Response => {

                            Swal.fire({
                                position: 'top-end',
                                icon: 'success',
                                title: 'Reservation has been accepted!',
                                showConfirmButton: false,
                                timer: 3500
                            })
                            setTimeout(() => window.location.reload(), 3500);
                        })
                }
            })
        },
        decline(id) {
            this.status = 'declined';
            Swal.fire({
                title: 'Are you sure you want to decline the reservation?',
                text: "Reservation will be declined",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#ffdff0',
                cancelButtonColor: '#c41088',
                confirmButtonText: 'Yes, Im sure!'
            }).then((result) => {
                if (result.isConfirmed) {
                    axios
                        .put(`rest/reservations/changeStatus/` + id + '/' + this.status)
                        .then(Response => {

                            Swal.fire({
                                position: 'top-end',
                                icon: 'success',
                                title: 'Reservation has been declined!',
                                showConfirmButton: false,
                                timer: 3500
                            })
                            setTimeout(() => window.location.reload(), 3500);
                        })
                }
            })
        },
        complete(id, to) {
            date = new Date(to);
            if (this.today < date) {
                Swal.fire({
                    icon: 'error',
                    title: 'Not allowed',
                    text: 'The reservation date hasnt passed',
                })
            }
            else {
                this.status = 'completed';

                Swal.fire({
                    title: 'Are you sure you want to complete the reservation?',
                    text: "Reservation will be completed",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#ffdff0',
                    cancelButtonColor: '#c41088',
                    confirmButtonText: 'Yes, Im sure!'
                }).then((result) => {
                    if (result.isConfirmed) {
                        axios
                            .put(`rest/reservations/changeStatus/` + id + '/' + this.status)
                            .then(Response => {

                                Swal.fire({
                                    position: 'top-end',
                                    icon: 'success',
                                    title: 'Reservation has been completed!',
                                    showConfirmButton: false,
                                    timer: 3500
                                })
                                setTimeout(() => window.location.reload(), 3500);
                            })
                    }
                })
            }

        },

        load(data) {
            this.reservations = data;
            for (let i = 0; i < this.reservations.length; i++) {
                console.log(this.reservations[i].status);
                if (this.reservations[i].status === 'created') {
                    this.reservationsCreated.push(this.reservations[i]);
                }
                else if (this.reservations[i].status === 'accepted') {
                    this.reservationsAccepted.push(this.reservations[i]);
                }
                else if (this.reservations[i].status === 'completed') {
                    this.reservationsCompleted.push(this.reservations[i]);
                }
                else if (this.reservations[i].status === 'declined') {
                    this.reservationsDeclined.push(this.reservations[i]);
                }
                else {
                    this.reservationsCanceled.push(this.reservations[i]);
                }

            }
        },
        failed() {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Something went wrong!',
            })
        }

    },
    created() {
        if (localStorage.getItem('jwt') == '') {
            this.$router.push('/login');

        }
        else {
            axios
                .get('rest/reservations/' + localStorage.getItem('role') + '/' + localStorage.getItem('username'))
                .then(Response => this.load(Response.data));
        }

    }
})