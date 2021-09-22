package cn.xqhuang.dps.algorithms;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class TrieTree {

    /**
     * 内部节点类
     *
     * @author huangxq
     */
    @Getter
    @Setter
    public static class Node {

        // 该字串的重复数目，  该属性统计重复次数的时候有用, 取值为0、1、2、3、4、5……
        private int dumpLiNum;

        //以该字串为前缀的字串数， 应该包括该字串本身！！！！！
        private int prefixNum;

        // 此处用数组实现，当然也可以map或list实现以节省空间
        private Node childs[];

        private boolean isLeaf;

        ///是否为单词节点
        public Node() {
            dumpLiNum = 0;
            prefixNum = 0;
            isLeaf = false;
            childs = new Node[26];
        }
    }

    private Node root;

    // 树跟
    public TrieTree() {
        root = new Node();
    }

    /**
     * 插入字串，用循环代替迭代实现
     *
     * @param words
     */
    public void insert(String words) {
        insert(this.root, words.toLowerCase());
    }

    /**
     * 插入字串，用循环代替迭代实现
     * @param root
     * @param words
     */
    private void insert(Node root, String words) {
        char[] arr = words.toCharArray();

        //用相对于a字母的值作为下标索引，也隐式地记录了该字母的值
        int len =  arr.length;
        for (int i = 0; i < len; i++) {
             int index = arr[i] - 'a';
             // 如果不存在
             if (root.childs[index] == null) {
                 root.childs[index] = new Node();
             }
             // 该子节点prefix_num++
            root.childs[index].prefixNum++;

            //如果到了字串结尾，则做标记
            if (i == len - 1) {
                root.childs[index].isLeaf = true;
                root.childs[index].dumpLiNum++;
            }
        }
    }

    /**
     * 遍历Trie树，查找所有的words以及出现次数
     *
     */
    public HashMap<String, Integer> getAllWords() {
        return preTraversal(this.root, "");
    }

    private HashMap<String, Integer> preTraversal(Node root, String prefixs) {
        HashMap<String, Integer> map = new HashMap<>();

        if (root.isLeaf) {
            // 当前即为一个单词
            map.put(prefixs, root.getDumpLiNum());
        }
        int len = root.childs.length;
        for (int i = 0; i < len; i++) {
            if(root.childs[i] != null){
                char ch = (char) (i + 'a');
                String str = prefixs + ch;
                map.putAll(preTraversal(root.childs[i], str));
            }
        }

        return map;
    }
    /**
     * 判断某字串是否在字典树中
     * @param word
     * @return true if exists ,otherwise  false
     */
    public boolean isExist(String word){
        return search(this.root, word.toLowerCase());
    }
    /**
     * 查询某字串是否在字典树中
     * @param word
     * @return true if exists ,otherwise  false
     */
    private boolean search(Node root, String word) {
        char[] arr = word.toCharArray();
        int len = arr.length;

        for (int i = 0; i < len; i++) {
            int index = arr[i] - 'a';
            if (root.childs[index] == null) {
                return false;
            }
            /*if (i == len - 1 && root.childs[index].isLeaf) {
                return true;
            }*/
            root = root.childs[index];
        }
        return true;
    }

    /**
     * 得到以某字串为前缀的字串集，包括字串本身！ 类似单词输入法的联想功能
     * @param prefix 字串前缀
     * @return 字串集以及出现次数，如果不存在则返回null
     */
    public HashMap<String, Integer> getWordsForPrefix(String prefix) {
        return getWordsForPrefix(this.root, prefix);
    }

    /**
     * 得到以某字串为前缀的字串集，包括字串本身！
     * @param root
     * @param prefix
     * @return 字串集以及出现次数
     */
    private HashMap<String, Integer> getWordsForPrefix(Node root,String prefix){
        char[] chrs=prefix.toLowerCase().toCharArray();

        for(int i=0, length=chrs.length; i<length; i++){

            int index=chrs[i]-'a';
            if(root.childs[index]==null){
                return null;
            }

            root=root.childs[index];

        }
        ///结果包括该前缀本身
        ///此处利用之前的前序搜索方法进行搜索
        return preTraversal(root, prefix);
    }

    public static void main(String[] args) {
        TrieTree trie = new TrieTree();
        trie.insert("I");
        trie.insert("Love");
        trie.insert("China");
        trie.insert("China");
        trie.insert("China");
        trie.insert("China");
        trie.insert("China");
        trie.insert("xiaoliang");
        trie.insert("xiaoliang");
        trie.insert("man");
        trie.insert("handsome");
        trie.insert("love");
        trie.insert("chinaha");
        trie.insert("her");
        trie.insert("know");

        HashMap<String,Integer> map=trie.getAllWords();

        for(String key:map.keySet()){
            System.out.println(key+" 出现: "+ map.get(key)+"次");
        }


        map=trie.getWordsForPrefix("chin");

        System.out.println("\n\n包含chin（包括本身）前缀的单词及出现次数：");
        for(String key:map.keySet()){
            System.out.println(key+" 出现: "+ map.get(key)+"次");
        }

        if (trie.isExist("xiaoming")==false) {
            System.out.println("\n\n字典树中不存在：xiaoming ");
        }
    }
}