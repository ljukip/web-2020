Vue.component("home", {
  template: `<div id="homepage">
  <div>
    <h1 style="margin-top:10px;text-align: center;color:#c41088;">Welcome </h1>
    <hr style='background:#c41088;height:4px;'>
    <div id="Div-panel" style="background-image: url('images/apartment3.png'); background-size: cover; height: 86vh;">
      <h1>Wide range of rooms and apartments</h1>
      <button class="oval" style="    display: inline-block;
      position: absolute;
      right: 3px;
      top: 51px;
      background-size: cover;
      background-image: url('images/click.jpg');display: inline-block;"@click="$router.push('/apartments')"><p></p> </button>
    </div>
  </div>
</div>`,
});
