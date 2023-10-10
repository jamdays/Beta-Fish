## Connect 4 Bot
This is a connect 4 bot that, unsuprisingly, plays connect 4. 
I implemented minimax with alpha-beta pruning, and I am pretty happy with the result. 
I cannot win against the no-bitboards implementation, and I usually lose against the bit boards implementation
(See **Other**, 1.). 

## Classes
- ### Bitboards
  - An implemenation of the connect 4 board as a bitboard. I didn't use any libraries for this, just the built in
    functions for bit shifting and anding and so on. It was a pretty difficult proccess, but it did allow the bot to
    play around 1 or 2 moves than the non-bitboard version.
    - *Note: 1-2 moves more doesn't sound like a lot, but this means that bitboards are up to 49x faster than regular boards*
        - *(7 columns per turn: 7\*7 = 49)*
    - I implemented the bitboard as an array of ints. One int for every row because I thought it would make it easier.
    - *Note: I could have done one long as the board, but it would be pretty annoying to implement, I think*
- ### Board 
  - This is the regular board (no bitboards), just an array of ints, 1 for player 1, 2 for player 2, and 0 for empty. 
- ### Main
  - Pretty much unused. I did all my testing within the Robo class, because it was easier. 
- ### Robo
  - This is where the implementation of minimax and alpha beta pruning are. One for bitboards and one for normal boards.
    - *Note: the keen eye will also see the 80 lines for playing with a hashset*
      - The idea was that I would not search a board state I had already seen before.    
      - This was before I found out about bitboards, and I was trying to optimize the play with regular boards.
      - Turned out it didn't help that much. 
  - Testing is also in here.

## Other 
1. Evaluation function
    - The evaluation function for bitboards is just distance from center. 
    - This is not the best, but it works. The bot can just easily out calculate the human anyway. 
      But the bot doesn't consider the even-odd strategy, so it is possible to win against it. 
      - Even odd strategy: https://youtu.be/YqqcNjQMX18?si=ZDzoL7cCBSkrql0m
    - The evaluation function for normal boards is similar, but it also takes the even-odd strategy into account 
      (along with some other stuff), so I haven't won against it yet.
    - But it also searches one depth less than bitboards, just because of time. Also, it was fun implementing bitboards,
      so at least I have that to show for it.
2. Possible Improvements
   - Opening book: have a book of openings, so it has to calculate less in the beginning (when there are more options)
   - Bitboard.toBoard() function that makes the bitboard into a board, or at least a matrix of ints
     - This would make it easier to write complex eval functions for bitboards.
   - Better eval function
     - I designed the eval function myself, so there is like a 100% chance that there is a better eval function.
   - And More!
     - There are def more ways to improve the project, but none that I can think of right now. 
       Let me know if you think of any (or just clone the project and implement them yourself ofc)
3. Also, yes, I know connect 4 is solved. I low-key wish it wasn't though