from setuptools import setup

setup(
    name='test_script',
    version='0.1.0',
    py_modules=['datafold_script'],
    install_requires=[
        # List any dependencies required by your script
        'numpy>=1.21.0',
        'scipy>=1.7.3',
        'scikit-learn>=0.24.2',
        'networkx>=2.5',
        'datafold>=1.1.6',
        'matplotlib>=3.4.3',
    ],
    entry_points={
        'console_scripts': [
            'datafold_script=datafold_script:main',
        ],
    },
    python_requires='>=3.6', 
)
