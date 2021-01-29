Vue.component("apartments", {
    data: function () {
        return {
            role: localStorage.getItem('role'),
            username: localStorage.getItem('username'),
            apartments: [],
            tableData: [],
            table: null,
            apartment: {
                type: '',
                adress: '',
                rooms: '',
                price: '',
                status: ''
            },
            callID: '',
            reserveTrue: '',
            searchedQuery: '?',
            searchedApartment: {
                location: null,
                from: null,
                to: null,
                roomsMin: null,
                roomsMax: null,
                guests: null,
                priceMin: null,
                priceMax: null,
            },
            roomsMin: [1, 2, 3, 4, 5, 6, 7, 8, 9],
            roomsMax: [1, 2, 3, 4, 5, 6, 7, 8, 9],
            guests: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14],
            isSearch: true,
            searching: false,

        }
    },
    template: `<div>
    <h1>Apartments</h1>
    <hr style='background:#c41088;height:4px;'>
    <div style="flex-direction: column; margin-top: 56px; margin-bottom: 56px;" id="example-table-theme"></div>

    <div style="margin-right: 20px;">
        <link style='display:inline; margin: 15px;'>
        <img v-on:click='isSearch = !isSearch' src='./images/search.webp'
            style="display:inline; width: 27px; height: 27;">
        </link>
    </div>

    <div class='container ' id='search' v-show='isSearch'>
        <nav>
            <hr style='background:#c41088;height:4px;'>
            <label class="label1">Search</label>

            <form class="form-inline">

                <div class="proba" style="flex-direction: row; display: inline-flex;">
                    <vuejsDatepicker placeholder="Select Check-in Date" v-model="searchedApartment.from"
                        :highlighted="searchedApartment">
                    </vuejsDatepicker>
                    <p>-</p>
                    <vuejsDatepicker placeholder="Select Check-out Date" v-model="searchedApartment.to"
                        :highlighted="searchedApartment">
                    </vuejsDatepicker>
                </div>


                <input style="width:14% ;" v-model='searchedApartment.priceMin' type="text" placeholder="Price min"
                    aria-label="Search">

                <input style="width:14% ;" v-model='searchedApartment.priceMax' type="text" placeholder="Price max"
                    aria-label="Search">

                <input style="width:14% ;" v-model='searchedApartment.location' type="text" placeholder="Location"
                    aria-label="Search">

                <select v-model="searchedApartment.roomsMin">
                    <option disabled value="">Min No. of rooms</option>
                    <option v-for="room in roomsMin">{{room}}</option>
                </select>

                <select v-model="searchedApartment.roomsMax">
                    <option disabled value="">Max No. of rooms</option>
                    <option v-for="room in roomsMax">{{room}}</option>
                </select>

                <select v-model="searchedApartment.guests">
                    <option disabled value="">No. of guests</option>
                    <option v-for="guest in guests">{{guest}}</option>
                </select>

                <button class="button2" type="submit" v-on:click="search()">Search</button>
                <button style='margin-right:5px;' v-on:click="reset()" class="button2" type="button">Reset</button>
            </form>
            <hr style='background:#c41088;height:4px;'>
        </nav>
    </div>

    <hr style='background:#c41088;height:4px;'>
    <button class="button1" v-if="role==='HOST'" @click="$router.push('/addApartment')  ">New apartment</button>
    <hr v-if="role==='HOST'" style='background:#c41088;height:4px;'>
</div>`,
    methods: {

        createTable() {

        },
        search() {

            // console.log(this.dates);
            if (this.searchedApartment.location !== null) {
                this.searchedQuery += 'location=' + this.searchedApartment.location;
            }
            if (this.searchedApartment.from != null) {
                let from = this.searchedApartment.from.getTime();
                this.searchedQuery += '&from=' + from;
            }
            if (this.searchedApartment.to != null) {
                let to = this.searchedApartment.to.getTime();
                this.searchedQuery += '&to=' + to;
            }
            if (this.searchedApartment.roomsMin !== null) {
                this.searchedQuery += '&roomsMin=' + this.searchedApartment.roomsMin;
            }
            if (this.searchedApartment.roomsMax !== null) {
                this.searchedQuery += '&roomsMax=' + this.searchedApartment.roomsMax;
            }
            if (this.searchedApartment.guests !== null) {
                this.searchedQuery += '&guests=' + this.searchedApartment.guests;
            }
            if (this.searchedApartment.priceMin !== null) {
                this.searchedQuery += '&priceMin=' + this.searchedApartment.priceMin;
            }
            if (this.searchedApartment.priceMax !== null && this.searchedApartment.priceMin === null) {
                this.searchedQuery += '&priceMax=' + this.searchedApartment.priceMax;
            }
            if (this.searchedApartment.priceMax !== null && this.searchedApartment.priceMin !== null) {
                if (parseFloat(this.searchedApartment.priceMax) >= parseFloat(this.searchedApartment.priceMin)) {
                    this.searchedQuery += '&priceMax=' + this.searchedApartment.priceMax;
                }
                else {
                    alert('Max value of price must be grater then min value of price!')
                }
            }

            //Ovo obrisati
            console.log(`Trazite apartman:
                location: ${this.searchedApartment.location}
                roomsMin: ${this.searchedApartment.roomsMin}
                roomsMax: ${this.searchedApartment.roomsMax}
                guests: ${this.searchedApartment.guests}
                priceMin: ${this.searchedApartment.priceMin}
                priceMax: ${this.searchedApartment.priceMax}
                `);

            axios
                .get('rest/apartment/search/' + this.role + '/' + this.username + '/' + this.searchedQuery)
                .then(response => {
                    //this.apartments = response.data;
                    this.serching = true;
                    this.load(response.data);
                    this.searchedQuery = '?';
                });
        },
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
        load(data) {

            table.clearData();
            this.apartments = data;
            console.log("apartments:" + this.apartments);
            for (let i = 0; i < this.apartments.length; i++) {
                let s = '';
                console.log("apartments:" + this.apartments[i].type);
                if (this.apartments[i].status === 'inactive') { s = 'click to activate'; }
                else { s = this.apartments[i].status; }
                let p = '';
                if (this.apartments[i].pricePerNight) {
                    p = this.apartments[i].pricePerNight;
                }
                else { p = this.apartments[i].price; }

                this.tableData = [
                    {
                        id: this.apartments[i].id,
                        type: this.apartments[i].type,
                        address: this.apartments[i].location.adress.streetNum + ',' + this.apartments[i].location.adress.city + ',' + this.apartments[i].location.adress.zip,
                        rooms: this.apartments[i].rooms,
                        price: p,
                        status: s,
                        edit: 'click to edit',
                        details: 'click for details',
                        reserve: 'click to reserve'
                    }
                ];

                table.updateOrAddData(this.tableData);
            }
            if (this.role === 'GUEST') {
                table.addColumn({
                    title: "Reserve", field: "reserve", formatter: "button", cellClick: function (e, cell) {
                        localStorage.setItem("reserveID", cell.getRow().getData().id);
                        localStorage.setItem("reserveTrue", true);
                        window.location.assign('#/newReservation');
                    }
                }, false, "reserve");
            }
            if (!this.serching) {
                if (this.role === 'HOST' || this.role === 'ADMIN') {
                    table.addColumn({
                        title: "Delete", field: "delete", formatter: 'buttonCross', cellClick: function (e, cell) {
                            Swal.fire({
                                title: 'Are you sure you want to delete the apartment?',
                                text: "Apartment will be deleted!",
                                icon: 'warning',
                                showCancelButton: true,
                                confirmButtonColor: '#ffdff0',
                                cancelButtonColor: '#c41088',
                                confirmButtonText: 'Yes, Im sure!'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    console.log(cell.getRow().getData().id);
                                    axios
                                        .delete('rest/apartment/delete/' + cell.getRow().getData().id)
                                        .then(Response => {
                                            Swal.fire({
                                                position: 'top-end',
                                                icon: 'success',
                                                title: 'The appartment has been deleted',
                                                showConfirmButton: false,
                                                timer: 3500
                                            })
                                            setTimeout(() => window.location.reload(), 3500);
                                            window.location.reload();
                                        })
                                }
                            })
                        }
                    }, false, "status");
                    table.addColumn({
                        title: "Edit", field: "edit", cellClick: function (e, cell) {
                            localStorage.setItem("editID", cell.getRow().getData().id);
                            localStorage.setItem("editTrue", true);
                            window.location.assign('#/editApartment/');
                        }
                    }, false, "delete");

                    table.addColumn({
                        title: "Details", field: "details", cellClick: function (e, cell) {
                            localStorage.setItem("detailsID", cell.getRow().getData().id);
                            window.location.assign('#/apartmentDetails');
                        }
                    }, false, "delete");
                }

            }
            // deleteApartment(id) {}

        },
        reset() {
            this.searchedQuery = '?';
            window.location.reload();
        },
        edit() {


        },
    },
    mounted() {


        table = new Tabulator("#example-table-theme", {
            height: "auto",
            data: this.tableData,
            layout: "fitColumns",
            resizableColumns: true,
            columns: [
                { title: "Id", field: "id", editor: "input", hozAlign: "center", headerFilter: "number" },
                { title: "Type", field: "type", editorParams: { values: { "room": "Room", "apartment": "Apartment" } }, headerFilter: true, headerFilterParams: { values: { "room": "Room", "apartment": "Apartment", "": "" } } },
                { title: "Address", field: "address", editor: "input", headerFilter: "input" },
                { title: "Rooms", field: "rooms", editor: "input", hozAlign: "center", headerFilter: "number" },
                { title: "Price per night", field: "price", editor: "input", headerFilter: "number" },
                {
                    title: "Status", field: "status", headerSort: false, cellClick: function (e, cell) {
                        if (localStorage.getItem('role') === 'HOST' && cell.getRow().getData().status === 'click to activate') {
                            Swal.fire({
                                title: 'Are you sure you want to cchange the status?',
                                text: "Apartment will become active!",
                                icon: 'warning',
                                showCancelButton: true,
                                confirmButtonColor: '#ffdff0',
                                cancelButtonColor: '#c41088',
                                confirmButtonText: 'Yes, Im sure!'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    console.log(cell.getRow().getData().id);
                                    axios
                                        .put('rest/apartment/' + cell.getRow().getData().id)
                                        .then(Response => {
                                            Swal.fire({
                                                position: 'top-end',
                                                icon: 'success',
                                                title: 'The appartment has become active',
                                                showConfirmButton: false,
                                                timer: 3500
                                            })
                                            setTimeout(() => window.location.reload(), 3500);
                                            window.location.reload();
                                        })
                                }
                            })
                        }
                    }
                },
            ],
        });
    },
    created() {
        this.role = localStorage.getItem('role');
        this.username = localStorage.getItem('username');
        axios
            .get('rest/apartment/' + this.role + '/' + this.username)
            .then(response => {
                this.load(response.data)
            })
        /*if (localStorage.getItem("editID")) {
            this.$router.push('/editApartment');
            window.location.reload();
        }
        if (localStorage.getItem("reserveID")) {
            this.$router.push('/newReservation');
            window.location.reload();
        }*/
    },
    components: {
        vuejsDatepicker
    },
})