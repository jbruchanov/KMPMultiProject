package com.example.libsproject.ext

import nl.adaptivity.xmlutil.dom2.Node
import nl.adaptivity.xmlutil.core.impl.idom.INodeList

interface BfsContext {
    val index: Int
    val level: Int
}

internal fun INodeList.forEachBfsIndexed(block: BfsContext.(node: Node) -> Unit) {

}
