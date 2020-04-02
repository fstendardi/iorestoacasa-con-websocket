$(function () {
  new Vue({
    el: '#app',
    data: {
      stats: {
        totaleCasi: 0,
        totaleDecessi: 0
      },
      map: null,
      data: [],
      loading: true,
      comuni: [],
      province: [],
      comune: {},
      provinciaSegnalata: null
    },
    mounted: function () {
      var self = this;
      
      $.get('/api/province').then(function(province){
    	 self.province = province;
    	 self.loading = false;
    	 self.initWebSocket();
      });
           

    },
    watch: {
    	position: function(position){
    		if (this.map){
    			this.map.setView(position);
    		}
    	},
    	data: {
    		handler: function(newData, oldData){
    			if (!this.map){
    			  this.initMap();
    			}
    	        
    			var self = this;
    			
    			var bounds = L.latLngBounds();
    			
    			newData.forEach(function (data) {

    			  var oldEntry = oldData.find(function(x) {
    				  return x.id == data.id
    			  });
    			
    			  var markerContent = `
    				<h5>${data.denominazioneProvincia}</h5>
    				<b>Positivi:</b> ${data.totaleCasi} 
    				<br/>
    				<b>Guariti:</b> ${data.totaleGuariti}
    				<br/>
    				<b>Decessi:</b> ${data.totaleDecessi}`;
    			
    			  var marker;
    			  if (oldEntry && oldEntry.marker){
    				marker = oldEntry.marker;  
    				marker.setPopupContent(markerContent);
    			  } else {
    				  var point = L.latLng(data.lat, data.long);				  
    				  marker = L.marker(point).addTo(self.map).bindPopup(markerContent);
        			  data.marker = marker;
    				  bounds.extend(point);				  
    			  }
    			  
    			  var myIcon = L.divIcon(
    					  {
    						  html: data.totaleCasi,
    						  iconSize: L.point(60, 60)
    					  });
    			  
    			  marker.setIcon(myIcon);
    			  
    			  if (oldEntry != null && oldEntry.totaleCasi != data.totaleCasi){
    				  for (var i = 0; i < 8; i++) {
    				    
    					(
    					function(i){
    						var blur = i % 2 === 0;
    						  
    						setTimeout(function() {
    					    	marker.setOpacity(blur ? 0.2 : 1);
    					    }, 300 * i);
    					}
    					)(i);
    				  }
    				  
    			  }
    			  
    			
    			});
    			
    			if (bounds.isValid()){
    				self.map.fitBounds(bounds);	
    			}
    			
        	},
        	deep: false
    	}
    },
    
    methods: {
      initWebSocket: function(){
    	  var self = this;
    	  this.wsClient = initWebsocketClient();
    	  
    	  this.wsClient.connect({}, function(){
    		 self.wsClient.subscribe('/app/public/covid', function(message){
    			 var updatedData = JSON.parse(message.body);
    			 
    			 if (self.data && self.data.length){
    				 
    				 var newData = [].concat(self.data);
    				 
    				 updatedData.forEach(function(d){
    					 var index = self.data.findIndex(function(x) {
    						return x.id == d.id; 
    					 });
    					 
    					 if (index >= 0){
    						 d = Object.assign({}, self.data[index], d);
    						 newData[index] = d;
    					 }
    				 });
    				 
    				 self.data = newData;
    			 } else {
    				 self.data = updatedData;
    			 }
    		 }) ;
    		 
    		 self.wsClient.subscribe('/app/public/covid.stats', function(message){
    			 self.stats = JSON.parse(message.body);
    		 });
    	  });
    	  
      },	
      initMap: function () {
        var self = this;
        this.map = L.map('map');
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        }).addTo(this.map);
        
        this.map.locate({
        	setView: true,
        	maxZoom: 9
        });
        
        this.map.on('locationfound', function(e){
        	$.get('https://eu1.locationiq.com/v1/reverse.php', {
        		key: '1fc41fb1ddd76f',
        		lat:e.latitude,
        		lon: e.longitude,
        		format: 'json'
        	}).then(function(response){
        		if (response.address.postcode){
        			
        			$.get('/api/comune', {
        				cap: response.address.postcode
        			}).then(function(comune){
        				if (comune){
        					self.comune = comune;
                			self.provinciaSegnalata = comune.sigla;		
        				}
        			});        			
        			
        		}
        	});
        })

      },
      
      submitCase: function(status){
    	  if (this.provinciaSegnalata){
    		  this.wsClient.send('/app/public/covid/segnala', {}, JSON.stringify({
        		  provincia: this.comune ? this.provinciaSegnalata : null,
        	      status: status
        	  }));
    		  this.provinciaSegnalata = (this.comune) ? this.comune.sigla : null;
        	  this.$refs['segnalaCasoModal'].hide();  
    	  }
    	  
      }
    },
  });



})