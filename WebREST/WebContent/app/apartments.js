Vue.component("apartments", {
    data: function () {
        return {
            role: '',
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
            callID: ''

        }
    },
    template: `
    <div>
        <p>probaaa</p>
        <div style="flex-direction: column;" id="example-table-theme"></div>
    
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
                        details: 'click for details'
                    }
                ];

                table.updateOrAddData(this.tableData);
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
                    title: "Edit", field: "edit", formatter: "button", cellClick: function (e, cell) {
                        //edit(cell.getRow().getData().id);
                        //this.$router.push("editApartment");
                        localStorage.setItem("editID", cell.getRow().getData().id);
                        localStorage.setItem("editTrue", true);
                        window.location.reload();
                    }
                }, false, "delete");

                table.addColumn({ title: "Details", field: "details", cellClick: function (e, cell) { } }, false, "delete");
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
        if (localStorage.getItem("editTrue")) {
            this.$router.push('/editApartment');
            window.location.reload();
        }
    },
})