package sc.pathfinding;

/**
 * This is where I'm going to try to implement a super fast graph pathfinding 
 * algorithm. The idea is:
 * 1. Find the best path from start point to finish.
 * 2. Use this exact same path until a creep reports that the path is blocked.
 * 3. Quickly update the path to be from where the creep currently is to the end,
 *    and then check to see if taking the previous best path up until where the 
 *    creep reported a block is still the best path.
 * 4. If that is true, then just replace everything after the blocked node with 
 *    the new path.
 * 
 * I want to use a heuristic, so it will be a best-first traversal.
 * The aim would be to reduce usage to the point where the game can run on 
 * Android devices again, and I think it can be done.
 * 
 * The paths should probably decided before the loading screen disappears.
 * @author Development
 */
public class QuickFind {
    
}
