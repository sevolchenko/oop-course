Hi! You are reading my Chinese Checkers Game guide.
Origin rules (on English) you can find here: https://www.mastersofgames.com/rules/chinese-checkers-rules.htm
1. Launch project. 
	Execute ru.vsu.cs.checkers.Main.java or use .jar file.
2. Game
	Type "game" to console to start game.
	Follow the messages on the screen.
3. Simulation
	Type "simulation" to start simulation.
	Type full path of file to console.
	Wait for end of script.
4. Indices of game board.
	All the places on board have their unique indices.
	Field is divided on 12 sectors, which are triangles of 2 types.
		       .
		      /0\
		_____/___\_____
		\ 5 /\ 6 /\ 1 /
		 \ /11\ / 7\ /
		  |----+----|
		 / \10/ \ 8/ \
		/_4_\/_9_\/_2_\
		     \   /
		      \3/
		       .
	First type of triangle (up - directed) is sectors 0, 2, 4, 7, 9, 11:
	     .
	    / \
	   / 0 \
	  / 1 2 \
	 / 3 4 5 \
	/ 6 7 8 9 \
	-----------
	Second type of triangle (down - directed) is sectors 1, 3, 5, 6, 8, 10:
	-----------
	\ 9 8 7 6 /
	 \ 5 4 3 /
	  \ 2 1 /
	   \ 0 /
	    \ /
	     .
	Index of each place on board can be calculated by the formula:
		i = s * 10 + n
	Where s - number of sector
	      n - number of place in concrete triangle
	* the middle point's index is 120.
	* sectors 6 - 11 are shifted clochwise on half-size of place.
		This shift is due to the fact that the places on the field should be staggered.
		For example, joint of 0, 6, 7 and 1 sectors:
	     
	        /\
	      / 0  \
	    / 1   2  \  
	  / 3   4   5  \  
	/ 6   7   8   9  \ 
	------------------  -----------------
	 \ 69  68  67  66 /\ 19  18  17  16 /
	   \ 65  64  63 / 70 \ 15  14  13 /
	     \ 62  61 / 71  72 \ 12  11 /
	       \ 60 / 73  74  75 \ 10 /
  	         \/ 76  77  78  79 \/
                  ------------------
		9 is connected with: 66, 67, 8, 5
		66 is connected with: 19, 70, 63, 67, 9
       	 	70 is connected with: 19, 15, 72, 71, 63, 66
		19 is connected with: 18, 15, 70, 66
		72 is connected with: 15, 12, 75, 74, 71, 70
		etc.
Thats all I wanted to write here. Now you are fully prepared to game.
If you have questions, you can text me on e-mail: se.volchenko@ya.ru
Wish you good game!