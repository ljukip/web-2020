Vue.component("apartments", {
    data: function () {
        return {
            apartments: [],
            tableData: [],
            table: null,
            apartment: {
                type: '',
                adress: '',
                rooms: '',
                price: '',
                status: ''
            }

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
                console.log("apartments:" + this.apartments[i].type);
                this.tableData = [
                    { type: this.apartments[i].type, address: this.apartments[i].location, rooms: this.apartments[i].rooms, price: this.apartments[i].price, status: this.apartments[i].status }
                ];

                table.updateOrAddData(this.tableData);
            }
        }


    },
    mounted() {



        table = new Tabulator("#example-table-theme", {
            height: "auto",
            data: this.tableData,
            layout: "fitColumns",
            resizableColumns: true,
            columns: [
                { title: "Type", field: "type", editorParams: { values: { "room": "Room", "apartment": "Apartment" } }, headerFilter: true, headerFilterParams: { values: { "room": "Room", "apartment": "Apartment", "": "" } } },
                { title: "Address", field: "address", editor: "select", headerFilter: "number" },
                { title: "Rooms", field: "rooms", editor: "star", hozAlign: "center", headerFilter: "number" },
                { title: "Price per night", field: "price", editor: "input", headerFilter: "number" },
                { title: "Status", field: "status", hozAlign: "center", formatter: "tickCross", headerFilter: "tickCross", headerFilterParams: { "tristate": true }, headerFilterEmptyCheck: function (value) { return value === null } },
            ],
        });
    },
    created() {
        axios
            .get('rest/apartment')
            .then(response => {
                this.load(response.data)
            })

    }
})