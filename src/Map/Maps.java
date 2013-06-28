package Map;

public class Maps {

	private int[][] map = new int[13][19];
	
	private int[][] map1 = 
		{ { 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 } , 
		{ 1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 } , 
		{ 1 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 1 } ,
		{ 1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 } , 
		{ 1 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 1 } , 
		{ 1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 } ,
		{ 1 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 1 } , 
		{ 1 , 10 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 } ,
		{ 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 }};
	
	private int[][] map2 = 
		{ { 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 } , 
		{ 1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 } , 
		{ 1 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 1 } ,
		{ 1 , 0 , 2 , 2 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 2 , 2 , 0 , 1 } , 
		{ 1 , 0 , 0 , 0 , 0 , 0 , 2 , 2 , 2 , 0 , 2 , 2 , 2 , 0 , 0 , 0 , 0 , 0 , 1 } , 
		{ 1 , 0 , 2 , 2 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 2 , 2 , 0 , 1 } ,
		{ 1 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 1 } , 
		{ 1 , 10 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 } ,
		{ 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 }};
	
	private int[][] map3 = 
		{ { 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 } , 
		{ 1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 } , 
		{ 1 , 0 , 2 , 0 , 2 , 0 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 0 , 2 , 0 , 2 , 0 , 1 } ,
		{ 1 , 0 , 0 , 0 , 0 , 0 , 0 , 2 , 2 , 2 , 2 , 2 , 0 , 0 , 0 , 0 , 0 , 0 , 1 } , 
		{ 1 , 0 , 2 , 0 , 2 , 0 , 2 , 2 , 1 , 2 , 1 , 2 , 2 , 0 , 2 , 0 , 2 , 0 , 1 } , 
		{ 1 , 0 , 0 , 0 , 0 , 0 , 0 , 2 , 2 , 2 , 2 , 2 , 0 , 0 , 0 , 0 , 0 , 0 , 1 } ,
		{ 1 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 1 } , 
		{ 1 , 10 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 } ,
		{ 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 }};
	
	public Maps( int map ){
		
		switch( map ){
			case 1:
				this.map = map1;
				break;
			case 2:
				this.map = map2;
				break;
			case 3:
				this.map = map3;
				break;
		}
		
		java.util.Random random = new java.util.Random();
		// ingresa cajas aleatorias 10 en este caso
		for( int i = 0; i < 30 ; i ++ ){
			
			boolean inserted = false;
			
			while( !inserted ){
				
				int x = random.nextInt( 19 );
				int y = random.nextInt( 9 );
				
				if( this.map[y][x] == 0 && 
					(( x > 0 && x < 18 ) && ( y > 0 && y < 8 )) && 
					!( y == 7 && x == 1) && !( y == 7 && x == 2) && !( y == 6 && x == 1)){
					this.map[y][x] = 3;
					inserted = true;
				}
			}
		}
		
		/* 5 bomb
		 * 6 fire
		 * 7 speed
		 * 8 life
		 * */
		boolean inserted;
		// en el primer mapa será una vida y un fuego
		// en el segundo mapa será una bomba y vida
		// en la tercera será una vida y un fuego y una bomba
		
		/* 2 vidas 5
		 * 1 bomba 3
		 * 1 fuego 3
		 * */
		
		switch( map ){
		case 1:
			inserted = false;
			while( !inserted ){
				int x = random.nextInt( 19 );
				int y = random.nextInt( 9 );
				if( this.map[y][x] == 0 && 
					(( x > 0 && x < 18 ) && ( y > 0 && y < 8 )) && 
					!( y == 7 && x == 1) && !( y == 7 && x == 2) && !( y == 6 && x == 1)){
					this.map[y][x] = 6;
					inserted = true;
				}
			}
			
			inserted = false;
			while( !inserted ){
				int x = random.nextInt( 19 );
				int y = random.nextInt( 9 );
				if( this.map[y][x] == 0 && 
					(( x > 0 && x < 18 ) && ( y > 0 && y < 8 )) && 
					!( y == 7 && x == 1) && !( y == 7 && x == 2) && !( y == 6 && x == 1)){
					this.map[y][x] = 8;
					inserted = true;
				}
			}
			
			inserted = false;
			while( !inserted ){
				int x = random.nextInt( 19 );
				int y = random.nextInt( 9 );
				if( this.map[y][x] == 0 && 
					(( x > 0 && x < 18 ) && ( y > 0 && y < 8 )) && 
					!( y == 7 && x == 1) && !( y == 7 && x == 2) && !( y == 6 && x == 1)){
					this.map[y][x] = 11;
					inserted = true;
				}
			}
			
			inserted = false;
			while( !inserted ){
				int x = random.nextInt( 19 );
				int y = random.nextInt( 9 );
				if( this.map[y][x] == 0 && 
					(( x > 0 && x < 18 ) && ( y > 0 && y < 8 )) && 
					!( y == 7 && x == 1) && !( y == 7 && x == 2) && !( y == 6 && x == 1)){
					this.map[y][x] = 11;
					inserted = true;
				}
			}
			
			break;
		case 2:
			inserted = false;
			while( !inserted ){
				int x = random.nextInt( 19 );
				int y = random.nextInt( 9 );
				if( this.map[y][x] == 0 && 
					(( x > 0 && x < 18 ) && ( y > 0 && y < 8 )) && 
					!( y == 7 && x == 1) && !( y == 7 && x == 2) && !( y == 6 && x == 1)){
					this.map[y][x] = 5;
					inserted = true;
				}
			}
			
			inserted = false;
			while( !inserted ){
				int x = random.nextInt( 19 );
				int y = random.nextInt( 9 );
				if( this.map[y][x] == 0 && 
					(( x > 0 && x < 18 ) && ( y > 0 && y < 8 )) && 
					!( y == 7 && x == 1) && !( y == 7 && x == 2) && !( y == 6 && x == 1)){
					this.map[y][x] = 8;
					inserted = true;
				}
			}
			
			inserted = false;
			while( !inserted ){
				int x = random.nextInt( 19 );
				int y = random.nextInt( 9 );
				if( this.map[y][x] == 0 && 
					(( x > 0 && x < 18 ) && ( y > 0 && y < 8 )) && 
					!( y == 7 && x == 1) && !( y == 7 && x == 2) && !( y == 6 && x == 1)){
					this.map[y][x] = 12;
					inserted = true;
				}
			}
			
			inserted = false;
			while( !inserted ){
				int x = random.nextInt( 19 );
				int y = random.nextInt( 9 );
				if( this.map[y][x] == 0 && 
					(( x > 0 && x < 18 ) && ( y > 0 && y < 8 )) && 
					!( y == 7 && x == 1) && !( y == 7 && x == 2) && !( y == 6 && x == 1)){
					this.map[y][x] = 12;
					inserted = true;
				}
			}
			
			inserted = false;
			while( !inserted ){
				int x = random.nextInt( 19 );
				int y = random.nextInt( 9 );
				if( this.map[y][x] == 0 && 
					(( x > 0 && x < 18 ) && ( y > 0 && y < 8 )) && 
					!( y == 7 && x == 1) && !( y == 7 && x == 2) && !( y == 6 && x == 1)){
					this.map[y][x] = 12;
					inserted = true;
				}
			}
			
			break;
		case 3:
			inserted = false;
			while( !inserted ){
				int x = random.nextInt( 19 );
				int y = random.nextInt( 9 );
				if( this.map[y][x] == 0 && 
					(( x > 0 && x < 18 ) && ( y > 0 && y < 8 )) && 
					!( y == 7 && x == 1) && !( y == 7 && x == 2) && !( y == 6 && x == 1)){
					this.map[y][x] = 5;
					inserted = true;
				}
			}
			
			inserted = false;
			while( !inserted ){
				int x = random.nextInt( 19 );
				int y = random.nextInt( 9 );
				if( this.map[y][x] == 0 && 
					(( x > 0 && x < 18 ) && ( y > 0 && y < 8 )) && 
					!( y == 7 && x == 1) && !( y == 7 && x == 2) && !( y == 6 && x == 1)){
					this.map[y][x] = 6;
					inserted = true;
				}
			}
			inserted = false;
			while( !inserted ){
				int x = random.nextInt( 19 );
				int y = random.nextInt( 9 );
				if( this.map[y][x] == 0 && 
					(( x > 0 && x < 18 ) && ( y > 0 && y < 8 )) && 
					!( y == 7 && x == 1) && !( y == 7 && x == 2) && !( y == 6 && x == 1)){
					this.map[y][x] = 8;
					inserted = true;
				}
			}
			inserted = false;
			while( !inserted ){
				int x = random.nextInt( 19 );
				int y = random.nextInt( 9 );
				if( this.map[y][x] == 0 && 
					(( x > 0 && x < 18 ) && ( y > 0 && y < 8 )) && 
					!( y == 7 && x == 1) && !( y == 7 && x == 2) && !( y == 6 && x == 1)){
					this.map[y][x] = 13;
					inserted = true;
				}
			}
			
			inserted = false;
			while( !inserted ){
				int x = random.nextInt( 19 );
				int y = random.nextInt( 9 );
				if( this.map[y][x] == 0 && 
					(( x > 0 && x < 18 ) && ( y > 0 && y < 8 )) && 
					!( y == 7 && x == 1) && !( y == 7 && x == 2) && !( y == 6 && x == 1)){
					this.map[y][x] = 13;
					inserted = true;
				}
			}
			
			inserted = false;
			while( !inserted ){
				int x = random.nextInt( 19 );
				int y = random.nextInt( 9 );
				if( this.map[y][x] == 0 && 
					(( x > 0 && x < 18 ) && ( y > 0 && y < 8 )) && 
					!( y == 7 && x == 1) && !( y == 7 && x == 2) && !( y == 6 && x == 1)){
					this.map[y][x] = 13;
					inserted = true;
				}
			}
			
			inserted = false;
			while( !inserted ){
				int x = random.nextInt( 19 );
				int y = random.nextInt( 9 );
				if( this.map[y][x] == 0 && 
					(( x > 0 && x < 18 ) && ( y > 0 && y < 8 )) && 
					!( y == 7 && x == 1) && !( y == 7 && x == 2) && !( y == 6 && x == 1)){
					this.map[y][x] = 13;
					inserted = true;
				}
			}
			
			break;
		}
		
		
	}
	
	public int[][] getMap(){ return map; }

}
