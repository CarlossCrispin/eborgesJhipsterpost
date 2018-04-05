$.getJSON("https://cpv2api.com/posts/published/j-w-v", function(resp){
	if(resp.success){
		for (var i = 0; i < resp.data.length; i++) { 
    $('.posts ul').append('<li><a target="_blank" href="' + resp.data[i].link +'">' + resp.data[i].title + ' <span> ' +resp.data[i].views+ ' views</span> </a></li>');
    }
	}
});

$.getJSON("https://cpv2api.com/pens/showcase/j-w-v", function(resp){
	if(resp.success){
		for (var i = 0; i < 5; i++) { 
    $('.pens ul').append('<li><a target="_blank" href="' + resp.data[i].link +'">' + resp.data[i].title + ' <span> ' +resp.data[i].views+ ' views</span> </a></li>');
    }
	}
}); 


particlesJS("particles-js", {
  "particles": {
    "number": {
      "value": 33,
      "density": {
        "enable": true,
        "value_area": 1420.4657549380909
      }
    },
    "color": {
      "value": "#ffffff"
    },
    "shape": {
      "type": "triangle",
      "stroke": {
        "width": 0,
        "color": "#000000"
      },
      "polygon": {
        "nb_sides": 5
      },
      "image": {
        "src": "img/github.svg",
        "width": 100,
        "height": 100
      }
    },
    "opacity": {
      "value": 0.06313181133058181,
      "random": false,
      "anim": {
        "enable": false,
        "speed": 1,
        "opacity_min": 0.1,
        "sync": false
      }
    },
    "size": {
      "value": 11.83721462448409,
      "random": true,
      "anim": {
        "enable": false,
        "speed": 40,
        "size_min": 0.1,
        "sync": false
      }
    },
    "line_linked": {
      "enable": true,
      "distance": 150,
      "color": "#ffffff",
      "opacity": 0.4,
      "width": 1
    },
    "move": {
      "enable": true,
      "speed": 6,
      "direction": "none",
      "random": false,
      "straight": false,
      "out_mode": "out",
      "bounce": false,
      "attract": {
        "enable": false,
        "rotateX": 600,
        "rotateY": 1200
      }
    }
  },
  "interactivity": {
    "detect_on": "canvas",
    "events": {
      "onhover": {
        "enable": true,
        "mode": "repulse"
      },
      "onclick": {
        "enable": true,
        "mode": "push"
      },
      "resize": true
    },
    "modes": {
      "grab": {
        "distance": 400,
        "line_linked": {
          "opacity": 1
        }
      },
      "bubble": {
        "distance": 400,
        "size": 40,
        "duration": 2,
        "opacity": 8,
        "speed": 3
      },
      "repulse": {
        "distance": 200,
        "duration": 0.4
      },
      "push": {
        "particles_nb": 4
      },
      "remove": {
        "particles_nb": 2
      }
    }
  },
  "retina_detect": true
});
// miel
window.onload = function(){
    var nav_links = document.querySelectorAll('.hexagon.dropdown');
    for(var i = 0; i<nav_links.length; i++){
      nav_links[i].addEventListener('mousedown', function(e){
        var all_nav_links = document.querySelectorAll('.hexagon.dropdown');
        var elem = e.target.parentNode.parentNode;
        if(!elem.className.match(/active/gi)){
          for(var j = 0; j<all_nav_links.length; j++){
          if (all_nav_links[j].className.match(/active/gi)){
            all_nav_links[j].className = all_nav_links[j].className.replace(/ active/gi, '')
          }
        }
          elem.className += ' active';
        } else {
          elem.className = elem.className.replace(/ active/, '');
        }
      });
    }
    var scene = document.getElementById('scene');
    var parallax = new Parallax(scene);
  }