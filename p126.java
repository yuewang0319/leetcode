/*
126. Word Ladder II

Given two words (beginWord and endWord), and a dictionary's word list, find all shortest transformation sequence(s) from beginWord to endWord, such that:

Only one letter can be changed at a time
Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
For example,

Given:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log","cog"]

Return
  [
    ["hit","hot","dot","dog","cog"],
    ["hit","hot","lot","log","cog"]
  ]
*/

public class Solution {

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Map<String, Integer> map = new HashMap<>();
        Map<String, Set<String>> neighbors = new HashMap<>();
        neighbors.put(beginWord, new HashSet<>());
        map.put(beginWord, -1);
        Set<String> wordSet = new HashSet<>();
        for(String word : wordList){
            wordSet.add(word);
            map.put(word, -1);
            neighbors.put(word, new HashSet<>());
        }
        int distance = 0;
        boolean found = false;
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        while(!queue.isEmpty()){
            int len = queue.size();
            for(int i = 0; i < len; i++){
                String curWord = queue.poll();
                if(map.get(curWord) == -1 || map.get(curWord) > distance){
                    map.put(curWord, distance);
                }
                if(curWord.equals(endWord)){
                    found = true;
                }
                for(int j = 0; j < curWord.length(); j++){
                    char[] arr = curWord.toCharArray();
                    for(char c = 'a'; c <= 'z'; c++){
                        if(c != arr[j]){
                            arr[j] = c; 
                        }
                        String newWord = new String(arr);
                        if(wordSet.contains(newWord)){
                            if(map.get(newWord) == -1){
                                queue.offer(newWord);
                                neighbors.get(curWord).add(newWord);
                            }
                        }
                    }
                }
            }
            if(found) break;
            distance++;
        }
        List<List<String>> wraplist = new ArrayList<>();
        dfs(map, neighbors, new ArrayList<>(), wraplist, beginWord, endWord);
        return wraplist;
    }
    
    public void dfs(Map<String, Integer> map, Map<String, Set<String>> neighbors, List<String> sublist, List<List<String>> wraplist, String curWord, String endWord){
        
        if(curWord.equals(endWord)){
            sublist.add(curWord);
            wraplist.add(new ArrayList<>(sublist));
            sublist.remove(sublist.size() - 1);
            return;
        }
        
        for(String neighbor : neighbors.get(curWord)){
            sublist.add(curWord);
            if(map.get(neighbor) == map.get(curWord) + 1){
                dfs(map, neighbors, sublist, wraplist, neighbor, endWord);
            }
            sublist.remove(sublist.size() - 1);
        }      
    }
}
