Vue.component("apartmentDetails", {
    data: function () {
        return {
            user: {
                username: '',
                role: ''
            },
            isAdmin: false,
            isHost: false,
            isGuest: false,
            apartmentId: 1,
            apartment: {
                type: null,
                rooms: null,
                guests: null,
                location: {
                    latitude: '',
                    longitude: '',
                    address: {
                        streetNum: '',
                        city: '',
                        zip: ''
                    }
                },
                period: {
                    to: null,
                    from: null
                },
                images: [],
                price: null,
                checkin: '',
                checkout: '',
                amenities: [],
                reviews: []
            },
            amenities: {
                basic: [],
                family: [],
                dining: [],
                recreation: [],
                pet: [],
                parking: [],
            },
            isOtherImgs: true,
            noReview: false,
            slideIndex: 1,
        }
    },
    template: `
<div >
	<div>
		<h1>Apartment<span id='titleEffect'>Details</span></h1>
		<hr style='background:#c41088;height:4px;'>
	</div>

	<div >
		<div class="col-lg-12">
			<div>
				<div id="slideshow">
					<div>
						<div :style="{'background-image': 'url(' + this.apartment.images[0] + ')'}" style="height: 100%;background-size: cover;"></div>
					</div>
					<div v-if="isOtherImgs" v-for="img in getOtherImgs">
						<div :style="{'background-image': 'url(' + img + ')'}" style="height: 100%;background-size: cover;">
						</div>
					</div>
				</div>
				
				<div class="Div-panel">
					<div >
						<h2 style="text-align: center;">Details</h2>
						<hr style='height:4px;border-top: 4px dotted #c41088;'>
					</div>
					<h2>Type:
						<span style="font-size: 21px;">{{apartment.type}}</span>
					</h2>

					<h2>Address:
						<span style="font-size: 21px;">{{apartment.location.adress.streetNum}} -
							{{apartment.location.adress.zip}} {{apartment.location.adress.city}}</span>
					</h2>

					<h2>Location:
						<span style="font-size: 21px;">longitude: {{apartment.location.longitude}} - latitude:
							{{apartment.location.latitude}}</span>
					</h2>

					<h2>Price:
						<span style="font-size: 21px;">{{apartment.pricePerNight}} $/per day</span>
					</h2>

					<h2>Rooms:
						<span style="font-size: 21px;">{{apartment.rooms}}</span>
					</h2>

					<h2>Number of guests:
						<span style="font-size: 21px;">{{apartment.capacity}}</span>
					</h2>

				</div>
			</div>

			
			<h2 style="text-align: center;">Amenities</h2>
			<hr style='height:4px;border-top: 4px dotted #c41088;'>
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
							<li><input :value="basic" v-model="apartment.amenities" type="checkbox"> {{basic.name}}</li>
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

			<div >
				<div >
					<h2 style="text-align: center;">Reviews</h2>
					<hr style='height:4px;border-top: 4px dotted #c41088;'>
				</div>

				<div v-for="review in apartment.reviews" v-if="review.published">
					<div style="margin-bottom: 10px;" id='star-rating'>
						<star-rating inactive-color="#e9c4d8"
						active-color="rgb(201, 28, 158)"
						glow:2 v-bind:read-only="true"
							v-bind:star-size="20" v-bind:show-rating="false" v-bind:rating="review.rating">
						</star-rating>
					</div>
					<p>{{review.review}}</p>
					<small class="text-muted">Posted by {{review.guestId}}</small>
					<hr>
				</div>

				<div v-show='noReview'>
					<h3>There are no reviews for this apartment...</h3>
				</div>
			</div>
		</div>
	</div>
</div>
</div> 
</div>
<!--apartment details-->
`
    ,

    methods: {
        plusDivs(n) {
            showDivs(slideIndex += n);
        },

        showDivs(n) {
            var i;
            var x = document.getElementsByClassName("mySlides");
            if (n > x.length) { slideIndex = 1 }
            if (n < 1) { slideIndex = x.length }
            for (i = 0; i < x.length; i++) {
                x[i].style.display = "none";
            }
            x[slideIndex - 1].style.display = "block";
        },
        getFirstImg: function () {
            //provera da li ima slika za dati stan
            if (!this.apartment.images.length) {
                img = ['./img/No_Image_Available.png'];
                // ako nema smesti noimage sliku
                this.apartment.images = img;
                console.log("image:" + this.apartment.images[0])
            }
        },
        arrangeAmenities() {
            for (let i = 0; i < this.apartment.amenities.length; i++) {
                if (this.apartment.amenities[i].type === 'basic') {
                    this.amenities.basic.push(this.apartment.amenities[i]);
                }
                else if (this.apartment.amenities[i].type === 'family_features') {
                    this.amenities.family.push(this.apartment.amenities[i]);
                }
                else if (this.apartment.amenities[i].type === 'dining') {
                    this.amenities.dining.push(this.apartment.amenities[i]);
                }
                else if (this.apartment.amenities[i].type === 'recreation') {
                    this.amenities.recreation.push(this.apartment.amenities[i]);
                }
                else if (this.apartment.amenities[i].type === 'pet') {
                    this.amenities.pet.push(this.apartment.amenities[i]);
                }
                else if (this.apartment.amenities[i].type === 'parking') {
                    this.amenities.parking.push(this.apartment.amenities[i]);
                }
            }
        },
        noComment: function () {
            console.log('this.apartment.reviews.length: ' + this.apartment.reviews.length);
            if (this.apartment.reviews === undefined || this.apartment.reviews.length === 0) {
                // console.log('this.apartment.reviews.length = 0 ');
                this.noReview = true;
            }
            else if (this.apartment.reviews !== undefined || this.apartment.reviews.length !== 0) {
                // console.log('this.apartment.reviews.length != 0 ');
                let visible = false;
                //ako ima komentara za dati stan, prolazimo kroz sve komentare 
                //i proveravamo da li su odobreni ako ni jedan nije odobren opet prikazujemo poruku
                for (let i = 0; i < this.apartment.reviews.length; i++) {
                    if (this.apartment.reviews[i].visible === false) {
                        // console.log('review.visible: ' + this.apartment.reviews[i].visible);
                        continue;
                    }
                    else {
                        visible = true;
                        // console.log('review.visible: ' + this.apartment.reviews[i].visible);
                        break;

                    }
                }
                //ako je visibilitu svakog komentara false onda 
                //prikazuje poruku kako nema komentara
                if (visible === false) {
                    this.noReview = true;
                }
            }
            else {

                this.noReview = false;
            }

        }
    },
    computed: {
        id() {
            return this.$route.params.id;
        },
        getOtherImgs: function () {
            imgs = this.apartment.images.slice(1);
            if (imgs.length === 0) {
                this.isOtherImgs = false;
            }
            else {
                this.isOtherImgs = true;
            }


            if (this.isOtherImgs) {
                $("#slideshow > div:gt(0)").hide();

                setInterval(function () {
                    $('#slideshow > div:first')
                        .fadeOut(1000)
                        .next()
                        .fadeIn(1000)
                        .end()
                        .appendTo('#slideshow');
                }, 3000);
            }

            return imgs;
        }
    },
    mounted() {
        this.user.username = localStorage.getItem('username');
        this.user.role = localStorage.getItem('role');

        if (this.user.role == "ADMIN") {
            this.isAdmin = true;
        } else if (this.user.role == "HOST") {
            this.isHost = true;
        } else {
            this.isGuest = true;
        }

        this.apartmentId = this.id;
        axios
            .get('rest/apartment/' + localStorage.getItem("detailsID"))
            .then(response => {
                this.apartment = response.data;
                this.getFirstImg();
                this.arrangeAmenities();
                this.noComment();
            })

    },
    created() {

    }

})
