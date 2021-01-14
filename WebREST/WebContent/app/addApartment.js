Vue.component("addApartment", {
    data: function () {
        return {
            username: localStorage.getItem('username'),
            role: localStorage.getItem('role'),
            types: ['room', 'apartment'],
            multi: true,
            apartment: {
                type: null,
                capacity: null,
                rooms: null,
                location: {
                    langitude: '',
                    latitude: '',
                    address: {
                        streetNum: '',
                        city: '',
                        zip: ''
                    }
                },
                price: null,
                checkin: '2 PM',
                checkout: '11 AM',
                to: null,
                from: null,
                host: '',
                amenities: [],
            },
            dates: {
                from: null,
                to: null
            },
            allAmenities: [], //svi amenties koji su u bazi
            amenities: {
                basic: [],
                family: [],
                dining: [],
                recreation: [],
                pet: [],
                parking: [],
            },
            roomCapacity: [1, 2, 3, 4, 5, 6, 7, 8, 9],
            guestCapacity: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14],
            messageVal: '',
            images: null,
            formData: ''

        }
    },
    template: `
    <div>
        <h1>New appartment</h1>
        <hr style='background:#c41088;height:4px;'>
        <div style="flex-direction: column;" id="Div-panel">
            
                
                <div id="div-form" style="width: 24%;">
                    <label class="label1">Type:</label>
                    <div v-if="messageVal==='wrongType'" style="color:  #c41088;text-align: center;font-family: cursive;font-size: 21;">Please select type</div>
                    <select id='listTypes'v-model="apartment.type">
                        <option disabled value="">Type</option>
                        <option v-for="type in types">{{type}}</option>
                    </select>
                </div>
                
                <div id="div-form" style="width: 24%;">
                    <label class="label1">Rooms:</label>
                    <div v-if="messageVal==='wrongRoom'" style="color:  #c41088;text-align: center;font-family: cursive;font-size: 21;">Please select </div>
                    <select id='listRooms' v-model="apartment.rooms">
                        <option disabled value="">Rooms</option>
                        <option v-for="room in roomCapacity">{{room}}</option>
                    </select>
                </div>
                
                <div id="div-form" style="width: 24%;">
                    <label class="label1">Guests:</label>
                    <div v-if="messageVal==='wrongGuest'" style="color:  #c41088;text-align: center;font-family: cursive;font-size: 21;">Please select </div>
                    <select id='listGuests' v-model="apartment.capacity">
                        <option disabled value="">Guests</option>
                        <option v-for="guest in guestCapacity">{{guest}}</option>
                    </select>
                </div>
                
                <div  id="div-form" style="width: 24%;">
                    <label class="label1">Price per night:</label>
                    <div v-if="messageVal==='wrongPrice'" style="color:  #c41088;text-align: center;font-family: cursive;font-size: 21;">Please enter </div>
                    <input style="width: fit-content;" v-model="apartment.price" type="text" name="price" placeholder="Enter price" >
                </div>
                
                <div  id="div-form" style="width: 56%;" >
                <label class="label1">location:</label>
                <div v-if="messageVal==='wrongLocation'" style="color:  #c41088;text-align: center;font-family: cursive;font-size: 21;">Please enter </div>
                    <div style="flex-direction: row; display: inline-flex;">
                        <input v-model="apartment.location.langitude" type="text" name="langitude" placeholder="Enter langitude" > 
                        <p>-</p>
                        <input v-model="apartment.location.latitude" type="text" name="latitude" placeholder="Enter latitude" >      
                    </div>
                </div>
                
                <div  id="div-form" style="width: 86%;">
                    <label class="label1">Address</label>
                    <div v-if="messageVal==='wrongAddress'" style="color:  #c41088;text-align: center;font-family: cursive;font-size: 21;">Please enter </div>
                    <div style="flex-direction: row; display: inline-flex;">
                        <input  v-model="apartment.location.address.streetNum" type="text" name="streetNum" placeholder="Street & Number" > 
                        <p>-</p>
                        <input v-model="apartment.location.address.city" type="text" name="city" placeholder="City" >
                        <p>-</p>
                        <input v-model="apartment.location.address.zip" type="text" name="zip" placeholder="Zip code" >      
                    </div>
                </div>
                
                <div  id="div-form" style="width: 34%;" >
                    <label class="label1">Check-in & Check-out:</label>
                    <div v-if="messageVal==='wrongCtime'" style="color:  #c41088;text-align: center;font-family: cursive;font-size: 21;">Please enter</div>
                    <div style="flex-direction: row; display: inline-flex;">
                        <input v-model="apartment.checkin" type="text" name="checkin" placeholder="Check-in time" > 
                        <p>-</p>
                        <input v-model="apartment.checkout" type="text" name="checkout" placeholder="Check-out time" >      
                    </div>
                </div>
                
                <div  id="div-form" style="width: 38%;" >
                    <label class="label1">From-to date:</label>
                    <div v-if="messageVal==='wrongDate'" style="color:  #c41088;text-align: center;font-family: cursive;font-size: 21;">Please select </div>
                    <div class="proba" style="flex-direction: row; display: inline-flex;">
                        <vuejsDatepicker  placeholder="Select Check-in Date"  v-model="dates.from"  :highlighted="dates">
                        </vuejsDatepicker> 
                        <p>-</p>  
                        <vuejsDatepicker placeholder="Select Check-outi Date" v-model="dates.to"  :highlighted="dates">
                        </vuejsDatepicker>   
                    </div>
                </div>
                <label class="label1">Amenities</label>
			<div  style="margin-top:20px; flex-direction: row;">
				<div  style="flex-direction: row;
                display: inline-flex;
                margin-left: 32px;
                margin-right: 32px;
                width:100%;
                min-width: -webkit-fill-available;"  >
					<div style="width: 20%;">
						<h4>Base</h4>
						<ul style="list-style: none; padding-left:0px" v-for="basic in amenities.basic">
							<li><input :value="b" vc-model="apartment.amenities" type="checkbox"> {{bas.name}}</li>
						</ul>
					</div>

					<div style="width: 20%;">
						<h4>Family</h4>
						<ul style="list-style: none; padding-left:0px" v-for="family in amenities.family">
							<li><input :value="family" v-model="apartment.amenities" type="checkbox"> {{family.name}}
							</li>
						</ul>
					</div>

					<div  >
						<h4>Dining</h4>
						<ul style="list-style: none; padding-left:0px" v-for="dining in amenities.dining">
							<li><input :value="dining" v-model="apartment.amenities" type="checkbox"> {{dining.name}}
							</li>
						</ul>
                    </div>
                    

					<div style="width: 20%;">
						<h4>Recreation</h4>
						<ul style="list-style: none; padding-left:0px" v-for="rec in amenities.recreation">
							<li><input :value="rec" v-model="apartment.amenities" type="checkbox"> {{rec.name}}</li>
						</ul>
                    </div>
                    
                    <div style="width: 20%;">
						<h4>Pet</h4>
						<ul style="list-style: none; padding-left:0px" v-for="pet in amenities.pet">
							<li><input :value="pet" v-model="apartment.amenities" type="checkbox"> {{pet.name}}</li>
						</ul>
                    </div>
                    
                    <div >
						<h4>Parking</h4>
						<ul style="list-style: none; padding-left:0px" v-for="rec in amenities.parking">
							<li><input :value="rec" v-model="apartment.amenities" type="checkbox"> {{rec.name}}</li>
						</ul>
					</div>

				</div>
            </div>
            

            
            <div id="center" style="flex-direction: row;">
                <button class="button1" type="submit" v-on:click='save()'>Save</button> 
                <button class="button1" type="button" v-on:click='cancel()' > Cancel</button> 
            </div>
                
            
            
            <div>
				<label >Upload images</label>
				<input type="file" name="files" id="files" ref="files"  multiple @change="uploadImage">
            </div>
        </div>
    
    </div>
    
    `,
    methods: {

        formatDate(dates) {
            return data.toLocaleDateString();
        },
        uploadImage() { },
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
        save() {
            console.log("in save" + this.dates.from + "to" + this.dates.to);

            console.log(this.apartment);
            if (this.apartment.type == null) {
                this.messageVal = 'wrongType';
                setTimeout(() => this.messageVal = '', 6000);
            }
            else if (this.apartment.location.latitude == '') {
                this.messageVal = 'wrongLocaton';
                setTimeout(() => this.messageVal = '', 6000);
            }
            else if (this.apartment.location.langitude == '') {
                this.messageVal = 'wronglocation';
                setTimeout(() => this.messageVal = '', 6000);
            }
            else if (this.apartment.location.address.streetNum == '') {
                this.messageVal = 'wrongAddress';
                setTimeout(() => this.messageVal = '', 6000);
            }
            else if (this.apartment.location.address.city == '') {
                this.messageVal = 'wrongAddress';
                setTimeout(() => this.messageVal = '', 6000);
            }
            else if (this.apartment.location.address.zip == '') {
                this.messageVal = 'wrongAddress';
                setTimeout(() => this.messageVal = '', 6000);
            }
            else if (this.apartment.rooms == null) {
                this.messageVal = 'wrongRoom';
                setTimeout(() => this.messageVal = '', 6000);
            }
            else if (this.apartment.capacity == null) {
                this.messageVal = 'wrongGuest';
                setTimeout(() => this.messageVal = '', 6000);
            }
            else if (this.apartment.price == null && isNaN(this.apartment.price)) {
                this.messageVal = 'wrongPrice';
                setTimeout(() => this.messageVal = '', 6000);
            }
            else if (this.apartment.checkin == null || this.apartment.checkout == null) {
                this.messageVal = 'wrongCtime';
                setTimeout(() => this.messageVal = '', 6000);
            }
            else if (this.dates.from == null) {
                this.messageVal = 'wrongDate';
                setTimeout(() => this.messageVal = '', 6000);
            }
            else {
                this.apartment.to = this.dates.from.getTime();
                this.apartment.from = this.dates.to.getTime();
                this.apartment.host = this.username;

                console.log("sending:" + this.apartment + this.username);
                axios
                    .post('rest/apartment/' + this.role, this.apartment)
                    .then(Response => {
                        console.log(Response);
                        if (this.images != null) {

                            let contentType = {
                                headers: {
                                    "Content-Type": "multipart/form-data"
                                }
                            }

                            axios
                                .post('rest/apartment/' + Response.data.id + '/upload', this.images, contentType)
                                .then(response => {
                                    console.log(response);
                                    this.messages.successResponse = `<h4>Apartment was added successfully!</h4>`;
                                    setTimeout(() => this.messages.successResponse = '', 6000);
                                });
                        }
                        if (Response.status === 201) {
                            Swal.fire({
                                position: 'top-end',
                                icon: 'success',
                                title: 'Apartment has been created',
                                showConfirmButton: false,
                                timer: 1400
                            })
                            this.$router.push('/apartments');
                        }
                    })
                    .catch(error => {
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: 'Something went wrong!',
                        })
                        console.log(response.data);
                    });
            }
        },
        uploadImage(e) {

            this.images = e.target.files;
            let formData = new FormData();
            for (let i = 0; i < this.images.length; i++) {
                formData.append('image', this.images[i], this.images[i].name);
            }
            this.images = formData;

            console.log("images:" + this.formData.image);
        },
        arrangeAmenities() {
            for (let i = 0; i < this.allAmenities.length; i++) {
                if (this.allAmenities[i].type === 'basic') {
                    this.amenities.basic.push(this.allAmenities[i]);
                }
                else if (this.allAmenities[i].type === 'family_features') {
                    this.amenities.family.push(this.allAmenities[i]);
                }
                else if (this.allAmenities[i].type === 'dining') {
                    this.amenities.dining.push(this.allAmenities[i]);
                }
                else if (this.allAmenities[i].type === 'recreation') {
                    this.amenities.recreation.push(this.allAmenities[i]);
                }
                else if (this.allAmenities[i].type === 'pet') {
                    this.amenities.pet.push(this.allAmenities[i]);
                }
                else if (this.allAmenities[i].type === 'parking') {
                    this.amenities.parking.push(this.allAmenities[i]);
                }
            }
        },

    },
    created() {
        if (!localStorage.getItem('jwt'))
            this.$router.push('/login');

        axios
            .get('rest/amenities/all/' + this.role)
            .then(response => {
                this.allAmenities = response.data;
                this.arrangeAmenities();
            })
    },
    mounted() {

        var map = new ol.Map({
            target: 'map',
            layers: [
                new ol.layer.Tile({
                    source: new ol.source.OSM()
                })
            ],
            view: new ol.View({
                center: ol.proj.fromLonLat([37.41, 8.82]),
                zoom: 4
            })
        });

    },
    components: {
        vuejsDatepicker
    },
})