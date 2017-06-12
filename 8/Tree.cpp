#include <iostream>
#include <vector>
#include <string>
#include <sstream>
#include <cctype>

std::vector<std::string> split(const std::string &str)
{
	int beg = 0, i = 0;
	std::vector<std::string> tmp;
	
	while (i < str.size())
	{
		while (isspace(str[i]))
			i++;
		
		if (i == str.size())
			continue;
		
		beg = i;
		while (!isspace(str[i]))
			i++;
		
		tmp.push_back(str.substr(beg, (i-beg)));
	}
	
	return tmp;
}

template<typename T> class Node
{
	public:
		T element;
		Node<T> *right;
		Node<T> *left;
	
		Node(T e) : element(e), right(nullptr), left(nullptr)
		{}
};

template<typename T> class BinaryTree
{
	private:
		Node<T> *Root;
		std::vector<int> maxLengths;
		const char ver[2] = {(char)179, '\0'};
		const char hor[2] = {(char)196, '\0'};
		const char up[2] = {(char)218, '\0'};
		const char dwn[2] = {(char)192, '\0'};
		
		void pA(const Node<T> *node) const;
		void pD(const Node<T> *node) const;
		void lol(const Node<T> *node, int x, int y);
		void lengthsOnLevels();
		int prt(Node<T> *node, int x, int y, std::vector<std::string> &list);
		void d(Node<T> *node);
		
	public:
		BinaryTree() : Root(nullptr) {}
		~BinaryTree() { clear(); }
		void insert(const T &e);
		void del(const T &e);
		void clear();
		bool search(const T &e) const;
		void draw();
		void printAscending() const { pA(Root); }
		void printDescending() const { pD(Root); }
};

class Wrapper
{
	private:
		BinaryTree<int> tree_int;
		BinaryTree<double> tree_double;
		BinaryTree<std::string> tree_string;
		
		enum class Type { INT, DOUBLE, STRING };
		Type type;
		
	public:
		Wrapper() : type(Type::STRING) {}
		void create(const std::vector<std::string> &tab);
		void insert(const std::vector<std::string> &tab);
		void del(const std::vector<std::string> &tab);
		bool search(const std::vector<std::string> &tab) const;
		void draw();
};


int main()
{
	Wrapper demo;
	std::string buffer;
	std::vector<std::string> tokens;
	
	std::cout << "Instrukcje:" << std::endl;
	std::cout << "-new (i/d/s)                - nowe drzewo int/double/string" << std::endl;
	std::cout << "-ins val1 [val2 val3 ...]   - dodanie elementu(ow) do drzewa" << std::endl;
	std::cout << "-del [val1 val2 ...]        - usuniecie calego drzewa lub podanych wartosci" << std::endl;
	std::cout << "-srch val                   - szukanie danej wartosci w drzewie" << std::endl;
	std::cout << "-draw                       - rysuje drzewo" << std::endl;
	std::cout << "-end                        - konczy program" << std::endl;
	
	while (true)
	{
		std::getline(std::cin, buffer);
		tokens = split(buffer);
		
		if (tokens[0] == "-new")
		{	
			demo.create(tokens);
		}
		else if (tokens[0] == "-ins")
		{
			demo.insert(tokens);
			demo.draw();
		}
		else if (tokens[0] == "-del")
		{	
			demo.del(tokens);
			demo.draw();
		}
		else if (tokens[0] == "-srch")
		{
			std::cout << demo.search(tokens);
		}
		else if (tokens[0] == "-draw")
		{
			demo.draw();
		}
		else if (tokens[0] == "-end")
		{
			std::cout << "Milo bylo poznac ;)\n";
			break;
		}
		else
			std::cout << "Nieprawidlowe polecenie. Przeczytaj instrukcje" << std::endl;
	}	
	
	//for (auto s : tab)
		//std::cout << s << "  " << s.size() << std::endl;
	//std::cout << text << std::endl;
	
	return 0;
}



template <typename T>
void BinaryTree<T>::lengthsOnLevels()
{
	maxLengths.clear();
	
	lol(Root, 0, 0);
	
	for (int i = 1; i < maxLengths.size(); i++)
		maxLengths[i]++;
}

template <typename T>
void BinaryTree<T>::lol(const Node<T> *node, int x, int y)
{
	if (node == nullptr)
		return;
	
	if (maxLengths.size() == y)
		maxLengths.push_back(0);
	
	std::stringstream ss;
	ss << node->element;
	
	if (maxLengths[y] < ss.str().size())
		maxLengths[y] = ss.str().size();
	
	lol(node->left, 2*x, y+1);
	lol(node->right, 2*x + 1, y+1);
}

template <typename T>
int BinaryTree<T>::prt(Node<T> *node, int x, int y, std::vector<std::string> &list)
{
	if (node == nullptr)
		return -1;
	
	std::vector<std::string> tmp;
	
	int posLeft = prt(node->left, 2*x, y+1, list);
	int posRight = prt(node->right, 2*x + 1, y+1,tmp);
	int pos = list.size();
	
	if (posLeft != -1)
	{
		list[posLeft].insert(0, up);
		
		for (int j = 1; j < maxLengths[y]; j++)
			list[posLeft].insert(0, " ");
		
		for (int i = 0; i < posLeft; i++)
		{
			for (int j = 0; j < maxLengths[y]; j++)
				list[i].insert(0, " ");
		}
		for (int i = posLeft + 1; i < list.size(); i++)
		{
			list[i].insert(0, ver);
			for (int j = 1; j < maxLengths[y]; j++)
				list[i].insert(0, " ");
		}
	}
	if (posRight != -1)
	{
		tmp[posRight].insert(0, dwn);
		
		for (int j = 1; j < maxLengths[y]; j++)
			tmp[posRight].insert(0, " ");
		
		for (int i = 0; i < posRight; i++)
		{
			tmp[i].insert(0, ver);
			for (int j = 1; j < maxLengths[y]; j++)
				tmp[i].insert(0, " ");
		}
		for (int i = posRight + 1; i < tmp.size(); i++)
		{
			for (int j = 0; j < maxLengths[y]; j++)
				tmp[i].insert(0, " ");
		}
	}
	
	
	list.push_back(std::string(""));
	
	std::stringstream ss;
	ss << node->element;
	
	for (int i = ss.str().size() + 1; i < maxLengths[y]; i++)
		list[pos].append(hor);
	
	if (ss.str().size() < maxLengths[y])
		list[pos].append(" ");
	
	list[pos].append(ss.str());
	
	if (posRight != -1)
		list.insert(list.end(), tmp.begin(), tmp.end());
	
	return pos;
}



template <typename T>
void BinaryTree<T>::insert(const T &e)
{
	Node<T> **tmp = &Root;
	
	/*if (Root != nullptr)
		std::cout << Root->element << "\n";
	if (*tmp != nullptr)
		std::cout << (*tmp)->element << "\n";*/
	
	while (*tmp != nullptr)
	{
		tmp = ((*tmp)->element < e) ? &(*tmp)->right : &(*tmp)->left;
	}
	
	*tmp = new Node<T>(e);
	
	//draw();
	//std::cout << std::endl;
}

template <typename T>
bool BinaryTree<T>::search(const T &e) const
{
	Node<T> *tmp = Root;
	
	while (tmp)
	{
		if (tmp->element == e)
			return true;
		if (tmp->element < e)
			tmp = tmp->right;
		else
			tmp = tmp->left;
	}
	return false;
}

template <typename T>
void BinaryTree<T>::del(const T &e)
{
	if (!search(e))
		return;
	
	Node<T> *parent = Root;
	Node<T> *del = Root;
	
	while (del->element != e)
	{
		parent = del;
		if (parent->element < e)
			del = parent->right;
		else
			del = parent->left;
	}
	
	if (del->right == nullptr)
	{
		if (del == Root)
		{	
			Root = del->left;
		}
		else
		{
			if (del == parent->right)
				parent->right = del->left;
			else
				parent->left = del->left;
		}
	}
	else if (del->left == nullptr)
	{
		if (del == Root)
		{	
			Root = del->right;
		}
		else
		{
			if (del == parent->right)
				parent->right = del->right;
			else
				parent->left = del->right;
		}
	}
	else
	{
		if (del == Root)
		{
			Root = del->right;
		}
		else
		{
			if (del == parent->right)
			{
				parent->right = del->right;
			}
			else 
			{
				parent->left = del->right;
			}
		}
		parent = del->right;
		while (parent->left)
			parent = parent->left;
		
		parent->left = del->left;
	}
	delete del;
	
	//draw();
	//std::cout << std::endl;
}

template <typename T>
void BinaryTree<T>::clear()
{
	d(Root);
	Root = nullptr;
}

template <typename T>
void BinaryTree<T>::d(Node<T> *node)
{
	if (node == nullptr)
		return;
	
	d(node->left);
	d(node->right);
	
	if (node->left)
		delete node->left;
	if (node->right)
		delete node->right;
}

template <typename T>
void BinaryTree<T>::pA(const Node<T> *node) const
{
	if (node == nullptr)
		return;
	pA(node->left);
	std:: cout << node->element << std::endl;
	pA(node->right);
}

template <typename T>
void BinaryTree<T>::pD(const Node<T> *node) const
{
	if (node == nullptr)
		return;
	pD(node->right);
	std::cout << node->element << std::endl;
	pD(node->left);
}

template <typename T>
void BinaryTree<T>::draw()
{
	lengthsOnLevels();
	
	std::vector<std::string> strArr;
	
	prt(Root, 0, 0, strArr);
	
	for (auto s : strArr)
		std::cout << s << std::endl;
}


void Wrapper::create(const std::vector<std::string> &tab)
{
	if (tab.size() != 2)
		return;
	
	if (tab[1] == "i")
	{
		type = Type::INT;
		tree_double.clear();
		tree_int.clear();
		tree_string.clear();
	}
	else if (tab[1] == "d")
	{
		type = Type::DOUBLE;
		tree_double.clear();
		tree_int.clear();
		tree_string.clear();
	}
	else if (tab[1] == "s")
	{
		type = Type::STRING;
		tree_double.clear();
		tree_int.clear();
		tree_string.clear();
	}
	else
		throw "Poprawne wartosci to:\ni   - integer\nd   - double\ns   - string\n";
}

void Wrapper::insert(const std::vector<std::string> &tab)
{
	switch (type)
	{
		case Type::INT:
		{
			std::stringstream ss;
			int val;
			
			for (int i = 1; i < tab.size(); i++)
			{
				ss.clear();
				ss.str(tab[i]);
				ss >> val;
				if (ss.fail() || !ss.eof())
				{
					throw tab[i] + " - nieprawidlowa wartosc. wpisz liczbe typu int.\n";
				}
				
				tree_int.insert(val);
			}
		}	
			break;
		case Type::DOUBLE:
		{
			std::stringstream ss;
			double val;
			
			for (int i = 1; i < tab.size(); i++)
			{
				ss.clear();
				ss.str(tab[i]);
				ss >> val;
				if (ss.fail() || !ss.eof())
				{
					throw tab[i] + " - nieprawidlowa wartosc. wpisz liczbe typu double.\n";
				}
				
				tree_double.insert(val);
			}
		}	
			break;
		case Type::STRING:
			for (int i = 1; i < tab.size(); i++)
			{	
				tree_string.insert(tab[i]);
			}
			break;
	}
}

void Wrapper::del(const std::vector<std::string> &tab)
{
	if (tab.size() == 1)
	{
		tree_int.clear();
		tree_double.clear();
		tree_string.clear();
		return;
	}
	
	switch (type)
	{
		case Type::INT:
		{
			std::stringstream ss;
			int val;
			
			for (int i = 1; i < tab.size(); i++)
			{
				ss.clear();
				ss.str(tab[i]);
				ss >> val;
				if (ss.fail() || !ss.eof())
				{
					throw tab[i] + " - nieprawidlowa wartosc. wpisz liczbe typu int.\n";
				}
				
				tree_int.del(val);
			}
		}	
			break;
		case Type::DOUBLE:
		{
			std::stringstream ss;
			double val;
			
			for (int i = 1; i < tab.size(); i++)
			{
				ss.clear();
				ss.str(tab[i]);
				ss >> val;
				if (ss.fail() || !ss.eof())
				{
					throw tab[i] + " - nieprawidlowa wartosc. wpisz liczbe typu double.\n";
				}
				
				tree_double.del(val);
			}
		}	
			break;
		case Type::STRING:
			for (int i = 1; i < tab.size(); i++)
			{	
				tree_string.del(tab[i]);
			}
			break;
	}
}

bool Wrapper::search(const std::vector<std::string> &tab) const
{
	if (tab.size() != 2)
		throw "tej opcji nalezy przekazac jeden argument\n";
	
	switch (type)
	{
		case Type::INT:
		{
			std::stringstream ss;
			int val;
			
			ss.str(tab[1]);
			ss >> val;
			if (ss.fail() || !ss.eof())
			{
				throw tab[1] + " - nieprawidlowa wartosc. wpisz liczbe typu int.\n";
			}
			
			return tree_int.search(val);
		}	
			break;
		case Type::DOUBLE:
		{
			std::stringstream ss;
			double val;
			
			ss.str(tab[1]);
			ss >> val;
			if (ss.fail() || !ss.eof())
			{
				throw tab[1] + " - nieprawidlowa wartosc. wpisz liczbe typu double.\n";
			}
			
			return tree_double.search(val);
		}	
			break;
		case Type::STRING:
				return tree_string.search(tab[1]);
			break;
	}
}

void Wrapper::draw()
{
	switch (type)
	{
		case Type::INT:
			tree_int.draw();
			break;
		case Type::DOUBLE:
			tree_double.draw();
			break;
		case Type::STRING:
			tree_string.draw();
			break;
	}
}