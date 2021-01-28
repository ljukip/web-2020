Vue.component("apartments", {
    data: function () {
        return {
            role: localStorage.getItem('role'),
            username: '',
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
            reserveTrue: ''

        }
    },
    template: `
    <div>
        <h1>Apartments</h1>
        <hr style='background:#c41088;height:4px;'>
        <div style="flex-direction: column; margin-top: 56px; margin-bottom: 56px;" id="example-table-theme"></div>
    
        <hr style='background:#c41088;height:4px;'>
        <button class="button1" v-if="role==='HOST'" @click="$router.push('/addApartment')  ">New apartment</button>
        <hr  v-if="role==='HOST'" style='background:#c41088;height:4px;'>
    </div>
    
    `,
    methods: {

        createTable() {


        },
        load(data) {
            this.apartments = data;
            console.log("apartments:" + this.apartments);
            for (let i = 0; i < this.apartments.length; i++) {
                let s = '';
                console.log("apartments:" + this.apartments[i].type);
                if (this.apartments[i].status === 'inactive') { s = 'click to activate'; }
                else { s = this.apartments[i].status; }
                this.tableData = [
                    {
                        id: this.apartments[i].id,
                        type: this.apartments[i].type,
                        address: this.apartments[i].location.adress.streetNum + ',' + this.apartments[i].location.adress.city + ',' + this.apartments[i].location.adress.zip,
                        rooms: this.apartments[i].rooms,
                        price: this.apartments[i].price,
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
            // deleteApartment(id) {}

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
})