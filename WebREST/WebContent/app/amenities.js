Vue.component("amenities", {
    data: function () {
        return {
            user: {
                role: localStorage.getItem('role')
            },
            amenity: {
                name: '',
                type: '',
            },
            newName: '',
            amenities: [],
            newClicked: 'false',
            editClicked: 'false',
            messageVal: '',
            types: ['basic', 'family_features', 'dining', 'recreation', 'pet', 'parking'],
        }
    },
    template: `
    <div style="height: 81.7%;">
    <h1 v-if="user.role ==='ADMIN'">Amenities managemnet</h1>
    <div id="filter">
        <nav>
            <hr style='background:#c41088;height:4px;'>
            <button v-if="newClicked==='false'" @click= "newClicked='true'" class="buttonNew">new amenity</button>
            <form @submit.prevent="" class="form-inline" v-if="newClicked==='true'">
                <div v-if="messageVal==='wrongName'" style="color:  #c41088;text-align: center;font-family: cursive;font-size: 21;">Please enter name</div>
                <div v-if="messageVal==='wrongType'" style="color:  #c41088;text-align: center;font-family: cursive;font-size: 21;">Please choose a type</div>
                <input style="width:14% ;" v-model="amenity.name" type="text" placeholder="Enter Name" name="name" required>
                <select id='listOfGenders'
                    v-model="amenity.type">
                    <option disabled value="">Type</option>
                    <option v-for="type in types">{{type}}</option>
                </select>
                
                <button class="button2" type="submit" v-on:click="save()">Save</button>
                <button style='margin-right:5px;' v-on:click="cancel()" class="button2" type="button">cancel</button>
            </form>
            <hr style='background:#c41088;height:4px;'>
        </nav>
    </div>    
    <div id="Div-panel" style="display: inline;">
            <div>
                <table class="myTable">
                    <thead>
                        <tr class="header">
                            <th>Name</th>
                            <th>Edit name</th>
                            <th>Type</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for='amenity in amenities'>
                            <td>{{amenity.name}}</td>
                            <td>
                                <button v-if="editClicked==='false'" class="buttonEdit" v-on:click= "editClick(amenity)" type="button"></button>
                                <div v-if="editClicked===amenity.id">
                                    <input style="width:14% ;" v-model="newName" type="text" placeholder="Enter New Name" name="new_name" required>
                                    <button class="button2" v-on:click= "editAmenity(amenity)" type="button">save</button>
                                    <button style='margin-right:5px;' v-on:click="cancel()" class="button2" type="button">cancel</button>
                                </div>
                            </td>
                            <td>{{amenity.type}}</td>
                            <td><button class="buttonDelete" v-on:click= "deleteAmenity(amenity)" type="button"></button></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        </div>
    </div>
    `,
    methods: {
        save() {
            if (this.amenity.name == '') {
                this.messageVal = 'wrongName';
                setTimeout(() => this.messageVal = '', 6000);
            }
            else if (this.amenity.type == '') {
                this.messageVal = 'wrongType';
                setTimeout(() => this.messageVal = '', 6000);
            }
            else {
                axios
                    .post('rest/amenities/' + this.user.role, this.amenity)
                    .then((responce) => this.succes(responce.data))
                    .catch(() => this.failed());
            }

        },
        succes: function (data) {
            console.log("succes");

            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Your work has been saved',
                showConfirmButton: false,
                timer: 1400
            })
            this.newClicked = 'false';
            window.location.reload();
        },
        failed: function () {
            console.log('nije prosao');
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Something went wrong!',
            })
        },
        editClick: function (amenity) {
            this.editClicked = amenity.id;
            console.log("usao u editClicked:" + this.editClicked);

        },
        cancel() {
            Swal.fire({
                title: 'Are you sure you want to cancel changes?',
                text: "The amenity won't be saved!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#ffdff0',
                cancelButtonColor: '#c41088',
                confirmButtonText: 'Yes, Im sure!'
            }).then((result) => {
                if (result.isConfirmed) {
                    this.newClicked = 'false';
                    if (this.editClicked != 'false') {
                        this.editClicked = 'false';
                    }
                }
            })
        },
        getAllAmenities() {
            //gets all users and puts them in users[]
            axios
                .get('rest/amenities/all/' + this.user.role)
                .then(Response => (this.amenities = Response.data));
        },
        deleteAmenity(amenity) {
            console.log("radi" + amenity.id);
            Swal.fire({
                title: 'Are you sure you want to delete the amenity?',
                text: "The amenity will be permanently deleted!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#ffdff0',
                cancelButtonColor: '#c41088',
                confirmButtonText: 'Yes, Im sure!'
            }).then((result) => {
                if (result.isConfirmed) {
                    axios
                        .delete('rest/amenities/delete/' + this.user.role + '/' + amenity.id)
                        .then(response => {
                            if (response.status == 201) {
                                Swal.fire({
                                    position: 'top-end',
                                    icon: 'success',
                                    title: 'Amenity has been deleted',
                                    showConfirmButton: false,
                                    timer: 1400
                                })
                                window.location.reload();
                            }
                            else {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Oops...',
                                    text: 'Something went wrong!',
                                })
                                console.log(response.data);
                            }
                        })
                }
            })

        },
        editAmenity(amenity) {
            console.log("novo ime za edit" + this.newName);
            amenity.name = this.newName;
            console.log("novo ime za edit" + amenity.name);
            axios
                .put('rest/amenities/edit/' + this.user.role + '/' + amenity.id + '/' + this.newName)
                .then(response => {
                    if (response.status == 201) {
                        Swal.fire({
                            position: 'top-end',
                            icon: 'success',
                            title: 'Amenity has been edited',
                            showConfirmButton: false,
                            timer: 1400
                        })
                        window.location.reload();
                    }
                    else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: 'Something went wrong!',
                        })
                        console.log(response.data);
                    }
                })
        }


    },
    created() {
        this.role = localStorage.getItem('role');
        if (this.role == "ADMIN") {
            this.getAllAmenities();
        }
    }
})