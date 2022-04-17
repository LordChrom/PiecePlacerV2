package puzzle;

//it's a linked list. Each element is a node with data and a reference to the next node.
// This is just used to keep track of what types of piece are available
//I used to use an array but most of the compute time is spent at the lower layers and iterating through a
//2 node linked list is faster than going through a 12 item array
public class LinkList {
    int type;
    LinkList next;
}
