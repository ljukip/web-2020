Vue.component("newReservation", {
    data: function () {
        return {
            username: localStorage.getItem("username"),
            apartment: '',
            apartmentId: localStorage.getItem("reserveID"),
            dates: {
                to: '',
                from: ''
            },
            disabledDates: {
                to: null,
                from: null,
                ranges: [],
            },

            night: null,
            nights: [],
            dates: {
                from: new Date,
                to: null,
                includeDisabled: true
            },
            reservation: {
                apartmentId: null,
                guestId: null,
                from: null,
                to: null,
                night: null,
                price: null,
                message: ""
            },
        }
    },
    template: `
<div>
    <h1>Create reservation</h1>
    <hr style='background:#c41088;height:4px;'>
    <div style="flex-direction: column;" id="Div-panel">
        
            
        <div id="div-form" style="width: 24%;">
            <label class="label1">Type:</label>
            <label class="label1">{{apartment.type}}</label>
        </div>
        
        <div id="div-form" style="width: 24%;">
            <label class="label1">Address:</label>
            <label class="label1">{{apartment.location.adress.streetNum}},
                <br>{{apartment.location.adress.city}},{{apartment.location.adress.zip}}</label>
        </div>    
        
        <div id="div-form" style="width: 24%;">
            <label class="label1">Price:</label>
            <label class="label1">{{apartment.pricePerNight}}</label>
        </div>
        <hr style='background:#c41088;height:4px;'>
        <label class="label1">Select Check-in Date:</label>
        <div>
            <vuejsDatepicker  :inline="true" :disabled-dates="disabledDates" :highlighted="dates"
                v-model="dates.from" placeholder="Select Check-in Date" v-on:input="calculate()">
            </vuejsDatepicker>
            <label class="label1">Number of nights:</label>
            <select style="width: -webkit-fill-available"  v-model="reservation.night"
                v-on:click="calculate()">
                <option disabled value="">No. of nights</option>
                <option v-for='night in nights'>{{night}}</option>
            </select>
        </div>

        <div>
            <label class="label1">Message:</label>
            <input type="text" style="width: 95%;" v-model='reservation.message' placeholder="Enter message..."></input>
        </div>

        <div id="center" style="flex-direction: row;">
            <button class="button1" type="submit" v-on:click='reserve()'>Reserve</button> 
            <button class="button1" type="button" v-on:click='cancel()' > Cancel</button> 
        </div>
        
    </div>

</div>
    `,
    methods: {
        reserve() {
            //todo: validacija
            console.log(this.dates.to.getTime(), this.apartment.pricePerNight)
            axios
                .post('rest/reservations', {
                    apartmentId: this.reservation.apartmentId,
                    guestId: this.username,
                    from: this.dates.from.getTime(),
                    to: this.dates.to.getTime(),
                    night: this.reservation.night,
                    price: this.apartment.pricePerNight,
                    message: this.reservation.message
                })
                .then((responce) => this.succes(responce.data))
                .catch(() => this.failed());
        },
        succes() {
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Reservation has been created',
                showConfirmButton: false,
                timer: 1400
            })
        },
        cancel() {
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
        load: function (data) {
            this.apartment = data;
            this.disabledDates.to = new Date(this.apartment.to);
            this.disabledDates.from = new Date(this.apartment.from);


            let today = new Date();
            if (this.disabledDates.to < today) {
                today.setDate(today.getDate() - 1);

                this.disabledDates.to = today;
            }
            this.insertReservationData();

            //check availability
            if (this.apartment.reservations != null) {
                for (let i = 0; i < this.apartment.reservations.length; i++) {
                    let available = {
                        from: new Date(this.apartment.reservations[i].from),
                        to: new Date(this.apartment.reservations[i].to + 1000 * 60 * 60 * 24)
                    }
                    this.disabledDates.ranges.push(available);
                }
            }

            localStorage.removeItem("reserveID");
        },
        calculate: function () {
            this.reservation.price = this.reservation.night * this.apartment.price;
            this.dates.to = new Date(this.dates.from.getTime() + this.reservation.night * 1000 * 60 * 60 * 24);

            this.nights = [];
            for (let i = 0; i < this.disabledDates.from.getDate() - this.dates.from.getDate(); i++) {
                // this.nights[i] = i + 1;
                this.nights = this.nights.concat(i + 1);
            }
        },
        insertReservationData: function () {
            this.reservation.apartmentId = this.apartmentId;
            this.reservation.guestId = this.username;
            this.reservation.night = 1;
            this.calculate();
        },

    },
    created() {
        if (!localStorage.getItem('jwt')) {
            this.$router.push('/login');
        }
        else {
            axios
                .get('rest/apartment/' + this.apartmentId)
                .then(Response => (this.load(Response.data)))
        }
    },
    components: {
        vuejsDatepicker
    },
})