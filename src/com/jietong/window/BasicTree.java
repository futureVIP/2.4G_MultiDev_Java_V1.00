package com.jietong.window;

import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.jietong.rfid.util.ReaderUtil;

public class BasicTree {
	/**
	 * 树形
	 */
	@SuppressWarnings("unused")
	public static void createTree(JTree tree, DefaultTreeModel model) {
		tree = new JTree(model);
		// 显示在线设备
		tree.setRootVisible(true);
		// 显示收缩箭头
		tree.setShowsRootHandles(true);
		// tree_onlineDevice.expandPath(new TreePath(node_1.getPath()));
		DefaultTreeCellRenderer render = (DefaultTreeCellRenderer) tree
				.getCellRenderer();
		// render.setLeafIcon(null);
		// render.setClosedIcon(null);
		// render.setOpenIcon(null);
		// 设置Tree的选择为一次只能选择一个节点的选项
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		// TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION 设置Tree的选择为一次选择多个节点
		// tree_onlineDevice.putClientProperty("JTree.lineStyle",Boolean.TRUE);
	}

	public static void getDeviceIP(DefaultTreeModel model, JTree tree,
			DefaultMutableTreeNode[] nodeTree, DefaultMutableTreeNode node) {
		node.removeAllChildren();
	}

	/***
	 * 刷新树，不更改树原来的展开状态
	 */
	public static void updateTree(DefaultMutableTreeNode[] nodeTree,
			DefaultTreeModel model, JTree tree, DefaultMutableTreeNode node_1) {
		Vector<TreePath> v = new Vector<TreePath>();
		BasicTree.getExpandNode(model, tree, node_1, v);
		getDeviceIP(model, tree, nodeTree, node_1);
		node_1.removeAllChildren();
		BasicTree.addNode(nodeTree, node_1, nodeTree.length);
		model.reload();
	}

	/**
	 * 完全展开一棵树或关闭一棵树
	 * 
	 * @param tree
	 *            JTree
	 * @param parent
	 *            父节点
	 * @param expand
	 *            true 表示展开，false 表示关闭
	 */
	@SuppressWarnings("rawtypes")
	public static void expandAll(JTree tree, TreePath parent, boolean expand) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();

		if (node.getChildCount() > 0) {
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path, expand);
			}
		}
		if (expand) {
			tree.expandPath(parent);
		} else {
			tree.collapsePath(parent);
		}
	}

	@SuppressWarnings("rawtypes")
	public static Vector<TreePath> getExpandNode(DefaultTreeModel model,
			JTree tree, TreeNode node, Vector<TreePath> v) {
		if (node.getChildCount() > 0) {
			TreePath treePath = new TreePath(model.getPathToRoot(node));
			if (tree.isExpanded(treePath))
				v.add(treePath);
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				getExpandNode(model, tree, n, v);
			}
		}
		return v;
	}

	public static void addNode(DefaultMutableTreeNode[] nodeTree,
			DefaultMutableTreeNode node, int n) {
		for (int i = 0; i < nodeTree.length; i++) {
			node.add(nodeTree[i]);
		}
	}

	/**
	 * 
	 * @param myTree
	 *            树
	 * @param currNode
	 *            展开节点的父节点
	 * @param vNode
	 *            展开节点，路径字符串|路径Node组成的Vector，按从根节点开始，依次添加到Vector
	 */
	public static void expandNode(JTree myTree,
			DefaultMutableTreeNode currNode, Vector<Object> vNode) {
		if (currNode.getParent() == null) {
			vNode.removeElementAt(0);
		}
		if (vNode.size() <= 0)
			return;

		int childCount = currNode.getChildCount();
		String strNode = vNode.elementAt(0).toString();
		DefaultMutableTreeNode child = null;
		boolean flag = false;
		for (int i = 0; i < childCount; i++) {
			child = (DefaultMutableTreeNode) currNode.getChildAt(i);
			if (strNode.equals(child.toString())) {
				flag = true;
				break;
			}
		}
		if (child != null && flag) {
			vNode.removeElementAt(0);
			if (vNode.size() > 0) {
				expandNode(myTree, child, vNode);
			} else {
				myTree.expandPath(new TreePath(child.getPath()));
			}
		}
	}

	public static void getDeviceComm(DefaultTreeModel model, JTree tree,
			DefaultMutableTreeNode[] nodeTree, DefaultMutableTreeNode node) {
		node.removeAllChildren();
		List<String> devCnt = ReaderUtil.readerService.findSerialPorts();
		if (devCnt.size() < 1) {
			return;
		}
		nodeTree = new DefaultMutableTreeNode[devCnt.size()];
		for (byte i = 0; i < devCnt.size(); ++i) {
			String comm = devCnt.get(i).toString();
			nodeTree[i] = new DefaultMutableTreeNode(comm);
		}
		int length = nodeTree.length;
		for (int i = 0; i < length; i++) {
			node.add(nodeTree[i]);
		}
		if (devCnt.size() > 0) {
			// Basic.cbbNetwork.setSelectedIndex(0);
		}
		// 效果一样
		// TreeNode root = (TreeNode) tree.getModel().getRoot();
		// model.reload(root);
		tree.updateUI();
	}
}
